package com.example.daitssuapi.domain.main.controller

import com.example.daitssuapi.common.dto.Response
import com.example.daitssuapi.domain.main.dto.request.CommentWriteRequest
import com.example.daitssuapi.domain.main.dto.response.CommentWriteResponse
import com.example.daitssuapi.domain.main.service.CommentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/daitssu/community/comment")
class CommentController(
    val commentService: CommentService
) {
    @PostMapping
    fun writeComment(
        @RequestBody commentWriteRequest: CommentWriteRequest
    ): Response<CommentWriteResponse>
            = Response(data = commentService.writeComment(commentWriteRequest))

}