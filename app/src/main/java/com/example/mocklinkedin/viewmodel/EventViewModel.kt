package com.example.mocklinkedin.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.model.PhotoModel
import com.example.mocklinkedin.repository.EventRepository
import com.example.mocklinkedin.repository.EventRepositorySharedPrefsImpl
import com.example.mocklinkedin.repository.PostRepository
import com.example.mocklinkedin.repository.PostRepositorySharedPrefsImpl
import java.io.File

private val empty = Event(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    shares = 0,
    views = 0,
    attachment = null
)

private val noPhoto = PhotoModel()
class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository = EventRepositorySharedPrefsImpl(application)
    val data = repository.getAllEvents()
    val edited = MutableLiveData(empty)

    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo

    fun saveEvent() {
        edited.value?.let {
            //repository.save(it)
            when(_photo.value) {
                noPhoto -> repository.saveEvent(it)
                else -> _photo.value?.file?.let { file ->
                    repository.saveEventWithAttachment(it, MediaUpload(file))
                }
            }
        }
        edited.value = empty
    }

    fun editEvent(event: Event) {
        edited.value = event
    }

    fun changeEventContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun changeEventPhoto(uri: Uri?, file: File?) {
        _photo.value = PhotoModel(uri, file)
    }

    fun likeEventById(id: Long) = repository.likeEventById(id)
    fun shareEventById(id: Long) = repository.shareEventById(id)
    fun removeEventById(id: Long) = repository.removeEventById(id)
}