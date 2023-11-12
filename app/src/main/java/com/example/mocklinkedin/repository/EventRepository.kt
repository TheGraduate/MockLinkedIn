package com.example.mocklinkedin.repository

import androidx.lifecycle.LiveData
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    val data: Flow<List<Event>>
    suspend fun getAllEvents()
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun likeEventById(id: Long)
    suspend fun unlikeEventById(id: Long)
    suspend fun shareEventById(id: Long)
    suspend fun saveEvent(event: Event)
    suspend fun removeEventById(id: Long)
    suspend fun saveEventWithAttachment(event: Event, upload: MediaUpload)
    suspend fun upload(upload: MediaUpload): Media

}