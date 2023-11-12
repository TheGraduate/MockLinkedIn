package com.example.mocklinkedin.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mocklinkedin.api.UsersApi
import com.example.mocklinkedin.dao.UserDao
import com.example.mocklinkedin.dto.Avatar
import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.dto.User
import com.example.mocklinkedin.entity.UserEntity
import com.example.mocklinkedin.entity.toDto
import com.example.mocklinkedin.entity.toEntity
import com.example.mocklinkedin.enumeration.AttachmentType
import com.example.mocklinkedin.error.ApiError
import com.example.mocklinkedin.error.NetworkError
import com.example.mocklinkedin.error.UnknownError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.IOException

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
   /* private val gson = Gson()
    private val prefs = context.getSharedPreferences("user_profile_prefs", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val userKey = "user"
    private var nextId = 0L
    private var users = emptyList<User>()
    private val data = MutableLiveData(users)
*/

    override val data = dao.getAll()
        .map(List<UserEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun upload(upload: MediaUpload): Media {
        try {
            val media = MultipartBody.Part.createFormData(
                "file", upload.file.name, upload.file.asRequestBody()
            )

            val response = UsersApi.service.upload(media)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    /* init {
         prefs.getString(userKey, null)?.let {
             users = gson.fromJson(it, type)
             nextId = users.maxByOrNull { it.id }?.id?.plus(1) ?: 1L
             data.value = users
         }
     }*/

    override suspend fun getAllUsers() {
        try {
            val response = UsersApi.service.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun saveUser(username: String, firstName: String, password: String) {
        try {
            val response = UsersApi.service.registration(username, firstName, password)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(UserEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }

    }

    override suspend fun registrationUser(username: String, firstName: String, password: String, upload: MediaUpload) {
        val media = upload(upload)
        //val userWithAvatar = copy(avatar = Avatar( media.id, AttachmentType.IMAGE))
        saveUser(username, firstName, password)
    }

    override suspend fun authentificationUser(username: String, password: String): Boolean {
        try {
            val response = UsersApi.service.authentication(username, password)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val authenticatedUser = response.body() ?: throw ApiError(response.code(), response.message())
            return authenticatedUser.nickName == username && authenticatedUser.password == password
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

  /*  private fun syncUsers() {
        with(prefs.edit()) {
            putString(userKey, gson.toJson(users))
            apply()
        }
    }*/
}