package com.example.daitssuapi.domain.main.service

import com.example.daitssuapi.common.enums.ErrorCode
import com.example.daitssuapi.common.exception.DefaultException
import com.example.daitssuapi.domain.main.dto.request.CommentWriteRequest
import com.example.daitssuapi.domain.main.dto.response.CommentWriteResponse
import com.example.daitssuapi.domain.main.model.entity.Comment
import com.example.daitssuapi.domain.main.model.repository.ArticleRepository
import com.example.daitssuapi.domain.main.model.repository.CommentRepository
import com.example.daitssuapi.domain.main.model.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {
    @Transactional
    fun writeComment(commentWriteRequest: CommentWriteRequest): CommentWriteResponse {
        if (commentWriteRequest.nickname == null)
            throw DefaultException(errorCode = ErrorCode.NICKNAME_REQUIRED)

        val user = userRepository.findByNickname(commentWriteRequest.nickname)
            ?: throw DefaultException(errorCode = ErrorCode.USER_NOT_FOUND)

        val article = articleRepository.findByIdOrNull(commentWriteRequest.articleId)
            ?: throw DefaultException(errorCode = ErrorCode.ARTICLE_NOT_FOUND)

        val comment = Comment(
            writer = user,
            article = article,
            content = commentWriteRequest.content,
        )

        val savedComment = commentRepository.save(comment)

        return CommentWriteResponse(
            nickname = user.nickname!!,
            content = commentWriteRequest.content,
            articleId = article.id,
            updatedAt = savedComment.updatedAt
        )
    }
}