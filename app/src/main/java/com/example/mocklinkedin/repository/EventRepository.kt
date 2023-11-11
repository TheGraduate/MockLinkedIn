package com.example.mocklinkedin.repository

import androidx.lifecycle.LiveData
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post

interface EventRepository {
    fun getAllEvents(): LiveData<List<Event>>
    fun likeEventById(id: Long)
    fun shareEventById(id: Long)
    fun saveEvent(event: Event)
    fun removeEventById(id: Long)
    fun saveEventWithAttachment(event: Event, upload: MediaUpload)
    //fun upload(upload: MediaUpload): Media

}