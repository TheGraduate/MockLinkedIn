package com.example.mocklinkedin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mocklinkedin.db.AppDb
import com.example.mocklinkedin.dto.Job
import com.example.mocklinkedin.model.FeedModel
import com.example.mocklinkedin.model.FeedModelState
import com.example.mocklinkedin.repository.JobRepository
import com.example.mocklinkedin.repository.JobRepositoryImpl
import com.example.mocklinkedin.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date

private val empty = Job(
    id = 0,
    name = "",
    position = "",
    start = Date(),
    finish = Date(),
    link = ""
)

class JobViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: JobRepository =
        JobRepositoryImpl(AppDb.getInstance(context = application).jobDao())
    val data: LiveData<FeedModel<Job>> = repository.data
        .map(::FeedModel)
        .asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    val edited = MutableLiveData(empty)
    private val _jobCreated = SingleLiveEvent<Unit>()
    val jobCreated: LiveData<Unit>
        get() = _jobCreated

    init {
        loadJobs()
    }

    fun loadJobs() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAllJobs()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun saveJob(
        id: Int,
        name: String,
        position: String,
        start: String?,
        finish: String?,
        link: String
    ) {
        edited.value?.let {
            _jobCreated.value = Unit
            viewModelScope.launch {
                try {
                    repository.saveJob(id, name, position, start, finish, link)
                    _dataState.value = FeedModelState()
                } catch (e: Exception) {
                    _dataState.value = FeedModelState(error = true)
                }
            }
        }
        edited.value = empty
    }

    fun edit(job: Job) {
        edited.value = job
    }

    fun changeContent(
        companyName: String,
        position: String,
        workStart: String,
        workFinish: String,
        companyLink: String
    ) {
        val companyNameText = companyName.trim()
        val positionText = position.trim()

        if (edited.value?.name == companyNameText &&
            edited.value?.position == positionText &&
            edited.value?.start.toString() == workStart &&
            edited.value?.finish.toString() == workFinish &&
            edited.value?.link == companyLink
            ) {
            return
        }

        edited.value = edited.value?.copy(name = companyNameText, position = positionText, start = Date(), finish = Date(), link = companyLink)
    }

    fun removeById(id: Long) {
        viewModelScope.launch {
            try {
                repository.removeById(id)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
}