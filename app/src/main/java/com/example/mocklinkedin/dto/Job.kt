package com.example.mocklinkedin.dto

import java.util.Date

data class Job(
    val id: Long,
    val name: String,
    val position: String,
    val start: Date,
    val finish: Date?,
    val link: String?,
)