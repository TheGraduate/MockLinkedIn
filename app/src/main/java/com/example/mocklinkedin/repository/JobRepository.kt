package com.example.mocklinkedin.repository

import com.example.mocklinkedin.dto.Job
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    val data: Flow<List<Job>>
    suspend fun getAll()
    suspend fun save(job: Job)
    suspend fun removeById(id: Long)
}