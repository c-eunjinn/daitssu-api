package com.example.daitssuapi.domain.main.service

import com.example.daitssuapi.common.exception.DefaultException
import com.example.daitssuapi.domain.main.dto.request.CommentWriteRequest
import com.example.daitssuapi.domain.main.model.repository.ArticleRepository
import com.example.daitssuapi.domain.main.model.repository.UserRepository
import com.example.daitssuapi.utils.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@IntegrationTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {
    @Test
    @DisplayName("성공_댓글 작성 요청 dto를 받아 댓글을 생성한다")
    fun success_write_comment_from_dto() {
        val article = articleRepository.findAll()[0]
        val user = userRepository.findAll().filter { null != it.nickname }[0]
        val commentWriteRequest = CommentWriteRequest(
            nickname = user.nickname,
            articleId = article.id,
            content = "예시 댓글"
        )

        val commentWriteResponse = commentService.writeComment(commentWriteRequest)

        assertEquals(commentWriteRequest.nickname, commentWriteResponse.nickname)
        assertEquals(commentWriteRequest.articleId, commentWriteResponse.articleId)
        assertEquals(commentWriteRequest.content, commentWriteResponse.content)
    }

    @Test
    @DisplayName("실패_dto에 닉네임이 없는 경우 NICKNAME_REQUIRED 에러를 발생시킨다")
    fun failure_no_nickname() {
        val article = articleRepository.findAll()[0]
        val commentWriteRequest = CommentWriteRequest(
            articleId = article.id,
            content = "예시 댓글"
        )

        assertThrows<DefaultException> { commentService.writeComment(commentWriteRequest) }
    }
}