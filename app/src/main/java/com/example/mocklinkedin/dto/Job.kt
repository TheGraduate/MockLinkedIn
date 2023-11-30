package com.example.mocklinkedin.dto

import java.util.Date

data class Job(
    val id: Long,
    val company: String,
    val position: String,
    val workStart: Date,
    val workFinish: Date,
)