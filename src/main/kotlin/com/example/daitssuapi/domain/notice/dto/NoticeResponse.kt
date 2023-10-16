package com.example.daitssuapi.domain.notice.dto

import com.example.daitssuapi.common.enums.NoticeCategory
import com.example.daitssuapi.domain.main.dto.response.ArticleResponse
import com.example.daitssuapi.domain.notice.model.entity.Notice
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.time.LocalDateTime

data class NoticeResponse(
    val id: Long?,
    val title: String,
    val departmentId: Int,
    val content: String,
    val category: NoticeCategory?,
    val imageUrl: String?,
    val fileUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun fromNotice(notice: Notice): NoticeResponse {
            return NoticeResponse(
                id = notice.id,
                title = notice.title,
                departmentId = notice.departmentId,
                content = notice.content,
                category = notice.category,
                imageUrl = notice.imageUrl,
                fileUrl = notice.fileUrl,
                createdAt = notice.createdAt,
                updatedAt = notice.updatedAt
            )
        }
    }
}