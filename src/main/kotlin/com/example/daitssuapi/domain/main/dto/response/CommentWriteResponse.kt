package com.example.daitssuapi.domain.main.dto.response

import java.time.LocalDateTime

data class CommentWriteResponse(
    val nickname: String,
    val content: String,
    val articleId: Long,
    val updatedAt: LocalDateTime
)