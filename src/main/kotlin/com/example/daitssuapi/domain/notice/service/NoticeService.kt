package com.example.daitssuapi.domain.notice.service

import com.example.daitssuapi.common.enums.ErrorCode
import com.example.daitssuapi.common.enums.NoticeCategory
import com.example.daitssuapi.common.exception.DefaultException
import com.example.daitssuapi.domain.notice.dto.NoticeResponse
import com.example.daitssuapi.domain.notice.model.entity.Notice
import com.example.daitssuapi.domain.notice.model.repository.NoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class NoticeService (
    private val noticeRepository: NoticeRepository,
){
    fun getNoticeList(
        category: String
    ):List<NoticeResponse> {

        val notices:List<Notice>

        if(category == "ALL")
            notices= noticeRepository.findAll()
        else{
            val noticeCategory = NoticeCategory.fromCode(category)
            if(noticeCategory !=null){
                notices = noticeRepository.findByCategory(noticeCategory)
            }else{
                throw DefaultException(errorCode = ErrorCode.INVALID_CATEGORY)
            }
        }


        return notices.map { NoticeResponse.fromNotice(it) }

    }
    fun getNoticePage(
        id: Long
    ):NoticeResponse{
        val notice :Notice = noticeRepository.findByIdOrNull(id)
            ?: throw DefaultException(errorCode= ErrorCode.NOTICE_NOT_FOUND)

        return NoticeResponse.fromNotice(notice)
    }

}


