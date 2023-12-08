package com.example.mocklinkedin.repository

import com.example.mocklinkedin.dto.Job
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    val data: Flow<List<Job>>
    suspend fun getAllJobs()
    suspend fun saveJob(
        id: Int,
        name: String,
        position: String,
        start: String?,
        finish: String?,
        link: String
    )
    suspend fun removeById(id: Long)
}