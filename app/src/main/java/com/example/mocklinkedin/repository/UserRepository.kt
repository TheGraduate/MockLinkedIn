package com.example.mocklinkedin.repository

import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

interface UserRepository {
    val data: Flow<List<User>>
    suspend fun getAllUsers()
   // fun getUser(): LiveData<User>
    //suspend fun saveUser(login: String, name: String, password: String)
    suspend fun upload(upload: MediaUpload): Media
    //suspend fun registrationUser(login: String, name: String, password: String, upload: MediaUpload): Response<User>
    //suspend fun authUser(login: String, password: String): Boolean

    suspend fun updateUser(login: String, pass: String): Response<ResponseBody>

    suspend fun registrationUser(login: String, pass: String, name: String, avatar: String?): Response<ResponseBody>
}