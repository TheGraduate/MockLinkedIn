package com.example.mocklinkedin.repository

import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val data: Flow<List<User>>
    suspend fun getAllUsers()
   // fun getUser(): LiveData<User>
    suspend fun saveUser(username: String, firstName: String, password: String)
    suspend fun upload(upload: MediaUpload): Media
    suspend fun registrationUser(username: String, firstName: String, password: String, upload: MediaUpload)
    suspend fun authentificationUser(username: String, password: String): Boolean
}