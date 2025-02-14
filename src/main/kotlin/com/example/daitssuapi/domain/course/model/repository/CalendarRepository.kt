package com.example.daitssuapi.domain.course.model.repository

import com.example.daitssuapi.domain.course.model.entity.Calendar
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface CalendarRepository : JpaRepository<Calendar, Long> {
    fun findByDueAtBetween(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<Calendar>
}