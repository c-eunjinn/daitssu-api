package com.example.daitssuapi.domain.main.dto.request

data class CommentWriteRequest(
    val nickname: String? = null,
    val content: String,
    val articleId: Long,
)