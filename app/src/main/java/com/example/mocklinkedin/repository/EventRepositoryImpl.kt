package com.example.mocklinkedin.repository

import com.example.mocklinkedin.api.EventsApi
import com.example.mocklinkedin.dao.EventDao
import com.example.mocklinkedin.dto.Attachment
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.entity.EventEntity
import com.example.mocklinkedin.entity.toDto
import com.example.mocklinkedin.entity.toEntity
import com.example.mocklinkedin.enumeration.AttachmentType
import com.example.mocklinkedin.error.ApiError
import com.example.mocklinkedin.error.AppError
import com.example.mocklinkedin.error.NetworkError
import com.example.mocklinkedin.error.UnknownError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.IOException

class EventRepositoryImpl(
    private val dao: EventDao
) : EventRepository{
    override val data = dao.getAll()
        .map(List<EventEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getAllEvents() {
        try {
            val response = EventsApi.service.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun saveEvent(event: Event) {
        try {
            val response = EventsApi.service.save(event)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override fun getNewerCount(id: Long): Flow<Int> = flow {
        while (true) {
            delay(10_000L)
            val response = EventsApi.service.getNewer(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity().map{
                it.copy()
            })
            emit(body.size)
        }
    }
        .catch { e -> throw AppError.from(e) }
        .flowOn(Dispatchers.Default)

    override suspend fun likeEventById(id: Long) {
        try {
            val response = EventsApi.service.likeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            dao.likeById(id)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override suspend fun unlikeEventById(id: Long) {

        try {
            val response = EventsApi.service.unlikeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override suspend fun removeEventById(id: Long) {
        try {
            val response = EventsApi.service.removeById(id)
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

    override suspend fun saveEventWithAttachment(event: Event, upload: MediaUpload) {
        try {
            val media = upload(upload)
            val eventWithAttachment = event.copy(attachment = Attachment(media.id, AttachmentType.IMAGE))
            saveEvent(eventWithAttachment)
        } catch (e: AppError) {
            throw e
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun upload(upload: MediaUpload): Media {
        try {
            val media = MultipartBody.Part.createFormData(
                "file", upload.file.name, upload.file.asRequestBody()
            )

            val response = EventsApi.service.upload(media)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    /*    override fun upload(upload: MediaUpload): Media {
            val media = MultipartBody.Part.createFormData(
                "file", upload.file.name, upload.file.asRequestBody()
            )

        }*/

    override suspend fun shareEventById(id: Long) {
        try {
            val response = EventsApi.service.shareById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            dao.onShare(id)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

   /* private fun syncEvent() {
        with(prefs.edit()) {
            putString(key, gson.toJson(events))
            apply()
        }
    }*/
}