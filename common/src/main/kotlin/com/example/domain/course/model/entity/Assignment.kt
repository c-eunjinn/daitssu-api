package com.example.domain.course.model.entity

import com.example.common.domain.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Assignment (
    val dueAt : LocalDateTime,
    
    val startAt : LocalDateTime,
    
    val name: String,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    var course : Course? = null,
) : BaseEntity()