package com.example.mocklinkedin.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.mocklinkedin.db.AppDb
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.Geo
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.model.FeedModel
import com.example.mocklinkedin.model.FeedModelState
import com.example.mocklinkedin.model.PhotoModel
import com.example.mocklinkedin.repository.EventRepository
import com.example.mocklinkedin.repository.EventRepositoryImpl
import com.example.mocklinkedin.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

import java.io.File

private val empty = Event(
    id = 0,
    author = "",
    content = "",
    published = 0,
    likedByMe = false,
    likes = 0,
    shares = 0,
    views = 0,
    geo = null,
    attachment = null
)

private val noPhoto = PhotoModel()
class EventViewModel(application: Application) : AndroidViewModel(application) {
   /* private val repository: EventRepository = EventRepositoryImpl(application)
    val data = repository.getAllEvents()
    val edited = MutableLiveData(empty)

    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo*/

    private val repository: EventRepository =
        EventRepositoryImpl(AppDb.getInstance(context = application).eventDao())

    val data: LiveData<FeedModel<Event>> = repository.data
        .map(::FeedModel)
        .asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    val newerCount: LiveData<Int> = data.switchMap {
        repository.getNewerCount(it.objects.firstOrNull()?.id ?: 0L)
            .catch { e -> e.printStackTrace() }
            .asLiveData(Dispatchers.Default)
    }

    val edited = MutableLiveData(empty)
    private val _eventCreated = SingleLiveEvent<Unit>()
    val eventCreated: LiveData<Unit>
        get() = _eventCreated

    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo

    init {
        loadEvents()
    }

    fun loadEvents() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAllEvents()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun refreshEvents() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(refreshing = true)
            repository.getAllEvents()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

     fun saveEvent(location: Geo, dateTime: Long) {
        edited.value?.let {
            _eventCreated.value = Unit
            viewModelScope.launch {
                try {
                    when(_photo.value) {
                        noPhoto -> repository.saveEvent(it, location, dateTime)
                        else -> _photo.value?.file?.let { file ->
                            repository.saveEventWithAttachment(it, MediaUpload(file), location, dateTime)
                        }
                    }

                    _dataState.value = FeedModelState()
                } catch (e: Exception) {
                    _dataState.value = FeedModelState(error = true)
                }
            }
        }
        edited.value = empty
        _photo.value = noPhoto
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

    fun likeEventById(id: Long) {

        viewModelScope.launch {
            try {
                repository.likeEventById(id)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }

    fun unlikeEventById(id: Long) {

        viewModelScope.launch {
            try {
                repository.unlikeEventById(id)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }

    fun shareEventById(id: Long) {
        viewModelScope.launch {
            try {

                repository.shareEventById(id)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
    fun removeEventById(id: Long) {
        viewModelScope.launch {
            try {
                repository.removeEventById(id)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
}