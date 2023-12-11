package com.example.mocklinkedin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.example.mocklinkedin.auth.AppAuth
import com.example.mocklinkedin.db.AppDb
import com.example.mocklinkedin.model.FeedModelState
import com.example.mocklinkedin.repository.UserRepository
import com.example.mocklinkedin.repository.UserRepositoryImpl
import com.example.mocklinkedin.util.SingleLiveEvent

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository =
        UserRepositoryImpl(AppDb.getInstance(context = application).userDao())

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    private val _authSuccess = SingleLiveEvent<Unit>()
    val authSuccess: LiveData<Unit>
        get() = _authSuccess

    fun updateUser(login: String, password: String) {
        viewModelScope.launch {
            try {
                val gson = Gson()
                val jsonObject = gson.fromJson(
                    repository.updateUser(login, password).body()?.toString(),
                    JsonObject::class.java
                )
                val id = jsonObject.get("id").asLong
                val token = jsonObject.get("token").asString
                AppAuth.getInstance().setAuth(id,token)
                _authSuccess.value = Unit
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }

    fun registrationUser(login: String, password: String, name: String, avatar: String?) {
        viewModelScope.launch {
            try {
                val gson = Gson()
                val jsonObject = gson.fromJson(
                    repository.registrationUser (login, password, name, avatar).body()?.string(),
                    JsonObject::class.java
                )
                val id = jsonObject.get("id").asLong
                val token = jsonObject.get("token").asString
                AppAuth.getInstance().setAuth(id,token)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
}