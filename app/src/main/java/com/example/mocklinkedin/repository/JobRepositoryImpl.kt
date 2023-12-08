package com.example.mocklinkedin.repository

import android.util.Log
import com.example.mocklinkedin.api.JobsApi
import com.example.mocklinkedin.dao.JobDao
import com.example.mocklinkedin.entity.JobEntity
import com.example.mocklinkedin.entity.toDto
import com.example.mocklinkedin.entity.toEntity
import com.example.mocklinkedin.error.ApiError
import com.example.mocklinkedin.error.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException

class JobRepositoryImpl(private val dao: JobDao): JobRepository{
    override val data = dao.getAll()
        .map(List<JobEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getAllJobs() {
        try {
            val response = JobsApi.service.getAll()
            if (!response.isSuccessful) {
                Log.e("JobRepositoryImpl", "Unsuccessful response: ${response.code()} - ${response.message()}")
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity())
        } catch (e: IOException) {
            Log.e("PostRepositoryImpl", "Exception: ${e.message}", e)
            throw NetworkError
        } catch (e: Exception) {
            Log.e("PostRepositoryImpl", "Exception: ${e.message}", e)
            throw com.example.mocklinkedin.error.UnknownError
        }
    }

    override suspend fun saveJob(
        id: Int,
        name: String,
        position: String,
        start: String?,
        finish: String?,
        link: String
    ) {

        try {
            val response = JobsApi.service.saveJob(id, name, position, start, finish, link)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(JobEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override suspend fun removeById(id: Long) {

        try {
            val response = JobsApi.service.removeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            dao.removeById(id)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }
}
