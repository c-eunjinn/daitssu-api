package com.example.daitssuapi.domain.main.controller

import com.example.daitssuapi.domain.main.dto.request.CommentWriteRequest
import com.example.daitssuapi.domain.main.model.repository.ArticleRepository
import com.example.daitssuapi.domain.main.model.repository.UserRepository
import com.example.daitssuapi.utils.ControllerTest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ControllerTest
class CommentControllerTest(
    val commentController: CommentController,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
) {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val baseUri = "/community/comment"

    @Test
    @DisplayName("성공_댓글 작성 요청 dto를 받아 200 코드와 댓글 작성 응답 dto를 반환한다.")
    fun success_write_comment_with_dto() {
        val user = userRepository.findAll().filter { null != it.nickname }[0]
        val article = articleRepository.findAll()[0]
        val commentWriteRequest = CommentWriteRequest(
            nickname = user.nickname,
            articleId = article.id,
            content = "댓글 내용"
        )

        val json = jacksonObjectMapper().writeValueAsString(commentWriteRequest)

        mockMvc.perform(
            post(baseUri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.data.nickname").value(user.nickname))
            .andExpect(jsonPath("$.data.content").value(commentWriteRequest.content))
            .andExpect(jsonPath("$.data.articleId").value(article.id))
    }
}