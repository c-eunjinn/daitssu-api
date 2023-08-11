package com.example.domain.course.service

import com.example.common.dto.Response
import com.example.common.enums.ErrorCode
import com.example.common.exception.DefaultException
import com.example.domain.course.dto.request.AssignmentRequest
import com.example.domain.course.dto.request.CalendarRequest
import com.example.domain.course.dto.response.CalendarResponse
import com.example.domain.course.dto.response.CourseResponse
import com.example.domain.course.dto.request.CourseRequest
import com.example.domain.course.dto.request.VideoRequest
import com.example.domain.course.dto.response.AssignmentResponse
import com.example.domain.course.dto.response.VideoResponse
import com.example.domain.course.model.entity.Assignment
import com.example.domain.course.model.entity.Calendar
import com.example.domain.course.model.entity.Course
import com.example.domain.course.model.entity.Video
import com.example.domain.course.model.repository.AssignmentRepository
import com.example.domain.course.model.repository.CalendarRepository
import com.example.domain.course.model.repository.CourseRepository
import com.example.domain.course.model.repository.VideoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
class CourseService (
    private val assignmentRepository: AssignmentRepository,
    private val courseRepository: CourseRepository,
    private val videoRepository: VideoRepository,
    private val calendarRepository: CalendarRepository,
) {
    fun getCourseList(): List<CourseResponse> {
        val courses: List<Course> = courseRepository.findAll()
        return courses.map { course ->
            CourseResponse(name = course.name, term = course.term)
        }
    }
    
    
    fun getCourse(
        courseId: Long
    ): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId)
            ?: throw DefaultException(errorCode = ErrorCode.COURSE_NOT_FOUND)
        
        val videoResponses = course.videos.map {
            VideoResponse(id = it.id, name = it.name, dueAt = it.dueAt, startAt = it.startAt)
        }
        
        
        val assignmentResponses = course.assignments.map {
            AssignmentResponse(id = it.id, name = it.name, dueAt = it.dueAt, startAt = it.startAt)
        }
        
        return CourseResponse(
            name = course.name,
            videos = videoResponses,
            assignments = assignmentResponses,
            term = course.term
        )
    }
    
    
    fun getCalendar(dateRequest: String): Map<String, List<CalendarResponse>> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date: LocalDateTime
        try {
            date = LocalDateTime.parse(dateRequest, formatter)
        } catch (e: DateTimeParseException) {
            throw DefaultException(errorCode = ErrorCode.INVALID_DATE_FORMAT)
        }
        
        val yearMonth = YearMonth.of(date.year, date.monthValue)
        val startDateTime = yearMonth.atDay(1).atStartOfDay()
        val endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59)
        
        return calendarRepository.findByDueAtBetween(startDateTime, endDateTime).groupBy(
            { it.course }, { CalendarResponse(it.type, it.dueAt, it.name) }
        )
        
    }
    
    
    fun postCalendar(calendarRequest: CalendarRequest) : CalendarResponse {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime:LocalDateTime
        try {
            dateTime = LocalDateTime.parse(calendarRequest.dueAt, formatter)
        } catch (e: DateTimeParseException) {
            throw DefaultException(errorCode = ErrorCode.INVALID_DATE_FORMAT)
        }
        val calendar = Calendar(
            type = calendarRequest.type,
            course = calendarRequest.course,
            dueAt = dateTime,
            name = calendarRequest.name
        ).also { calendarRepository.save(it) }
        
        return CalendarResponse(type = calendar.type, dueAt = calendar.dueAt, name = calendar.name)
    }
    
    
    fun postVideo(
        videoRequest: VideoRequest
    ) : VideoResponse {
        val course = courseRepository.findByIdOrNull(videoRequest.courseId)
            ?: throw DefaultException(errorCode = ErrorCode.COURSE_NOT_FOUND)
        
        val video = Video(
            dueAt = LocalDateTime.now().plusDays(7),
            startAt = LocalDateTime.now(),
            name = videoRequest.name,
            course = course
        ).also { videoRepository.save(it) }
        
        course.addVideo(video)
        
        return VideoResponse(id = video.id, name = video.name, dueAt = video.dueAt, startAt = video.startAt)
    }
    
    fun postAssignment(
        assignmentRequest: AssignmentRequest
    ) : AssignmentResponse {
        val course = courseRepository.findByIdOrNull(assignmentRequest.courseId)
            ?: throw DefaultException(errorCode = ErrorCode.COURSE_NOT_FOUND)
        
        
        val assignment = Assignment(
            dueAt = LocalDateTime.now().plusDays(7),
            startAt = LocalDateTime.now(),
            name = assignmentRequest.name,
            course = course
        ).also { assignmentRepository.save(it) }
        
        course.addAssignment(assignment)
        
        return AssignmentResponse(
            id = assignment.id,
            name = assignment.name,
            dueAt = assignment.dueAt,
            startAt = assignment.startAt
        )
    }
    
    fun postCourse(courseRequest: CourseRequest) : CourseResponse {
        val course = Course(courseRequest.name, courseRequest.term)
            .also { courseRepository.save(it) }
        
        return CourseResponse(name = course.name, term = course.term)
    }
}