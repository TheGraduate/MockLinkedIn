package com.example.mocklinkedin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mocklinkedin.dto.User
import com.example.mocklinkedin.repository.UserRepository
import com.example.mocklinkedin.repository.UserRepositoryImpl

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = UserRepositoryImpl(application)

    val user = repository.getUser()

    fun saveUser(userProfile: User) {
        repository.saveUser(userProfile)
    }
}