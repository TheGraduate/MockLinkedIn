package com.example.mocklinkedin.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mocklinkedin.dto.Attachment
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.enumeration.AttachmentType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class EventRepositorySharedPrefsImpl(
    context: Context,
) : EventRepository{
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("rep", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "events"
    private var nextId = 0L//1L
    private var events = emptyList<Event>()
    private val data = MutableLiveData(events)

    init {
        prefs.getString(key, null)?.let {
            events = gson.fromJson(it, type)
            nextId = events.maxByOrNull { it.id }?.id?.plus(1) ?: 1L
            data.value = events
        }
    }

    override fun getAllEvents(): LiveData<List<Event>> = data

    override fun saveEvent(event: Event) {
        if (event.id == 0L) {
            events = listOf(
                event.copy(
                    id = nextId++,
                    author = "Somebody",
                    likedByMe = false,
                    published = "yesterday"
                )
            ) + events
            data.value = events
            syncEvent()
            return
        }

        events = events.map {
            if (it.id != event.id) it else it.copy(content = event.content)
        }
        data.value = events
        syncEvent()
    }
    override fun likeEventById(id: Long) {
        events = events.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = events
        syncEvent()
    }
    override fun removeEventById(id: Long) {
        events = events.filter { it.id != id }
        data.value = events
        syncEvent()
    }

    override fun saveEventWithAttachment(event: Event, upload: MediaUpload) {
        //val media = upload(upload)
        val eventWithAttachment = event.copy(attachment = Attachment(/*media.id,*/ AttachmentType.IMAGE))
        saveEvent(eventWithAttachment)
    }

    /* override  fun upload(upload: MediaUpload): Media {
             val media = upload.file.name
                 MultipartBody.Part.createFormData(
                 "file", upload.file.name, upload.file.asRequestBody()
             )
             val response = upload(media)

             return response.body()
     }*/

    /*    override fun upload(upload: MediaUpload): Media {
            val media = MultipartBody.Part.createFormData(
                "file", upload.file.name, upload.file.asRequestBody()
            )

        }*/

    override fun shareEventById(id: Long) {
        events = events.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = events
        syncEvent()
    }

    private fun syncEvent() {
        with(prefs.edit()) {
            putString(key, gson.toJson(events))
            apply()
        }
    }
}