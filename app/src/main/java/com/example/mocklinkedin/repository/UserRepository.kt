package com.example.mocklinkedin.repository

import androidx.lifecycle.LiveData
import com.example.mocklinkedin.dto.User

interface UserRepository {
    fun getUser(): LiveData<User>
    fun saveUser(user: User)
}