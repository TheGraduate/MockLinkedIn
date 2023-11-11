package com.example.mocklinkedin.repository

import androidx.lifecycle.LiveData
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.dto.User

interface UserRepository {
    fun getAllUsers(): LiveData<List<User>>
   // fun getUser(): LiveData<User>
    fun saveUser(user: User)

    fun saveUserWithAvatar(user: User, upload: MediaUpload)
    fun UserExist(username: String, password: String): Boolean
}