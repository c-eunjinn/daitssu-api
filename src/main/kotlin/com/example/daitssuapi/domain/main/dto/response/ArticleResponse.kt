package com.example.daitssuapi.domain.main.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "단일 게시글 정보")
data class ArticleResponse(
    @Schema(description = "게시글 id")
    val id: Long,

    @Schema(
        description = "게시글 주제",
        allowableValues = ["잡담", "정보", "질문"]
    )
    val topic: String,

    @Schema(description = "게시글 제목")
    val title: String,

    @Schema(description = "게시글 내용")
    val content: String,

    @Schema(description = "작성자 닉네임")
    val writerNickName: String,

    @Schema(description = "마지막 수정된 시각")
    val updatedAt: LocalDateTime,

//    val images // image 데이터
)