package com.example.mocklinkedin.repository

import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
    val data: Flow<List<User>>
    suspend fun getAllUsers()
   // fun getUser(): LiveData<User>
    suspend fun saveUser(login: String, name: String, password: String)
    suspend fun upload(upload: MediaUpload): Media
    suspend fun registrationUser(login: String, name: String, password: String, upload: MediaUpload): Response<User>
    suspend fun authUser(login: String, password: String): Boolean
}