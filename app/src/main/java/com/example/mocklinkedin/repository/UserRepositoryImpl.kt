package com.example.mocklinkedin.repository

import com.example.mocklinkedin.api.UsersApi
import com.example.mocklinkedin.dao.UserDao
import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Token
import com.example.mocklinkedin.entity.UserEntity
import com.example.mocklinkedin.entity.toDto
import com.example.mocklinkedin.entity.toEntity
import com.example.mocklinkedin.error.ApiError
import com.example.mocklinkedin.error.NetworkError
import com.example.mocklinkedin.error.UnknownError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
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

    /*override suspend fun saveUser(login: String, name: String, password: String) {
        try {
            val response = UsersApi.service.registration(login, name, password)
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

    }*/

  /*  override suspend fun registrationUser(login: String, name: String, password: String, upload: MediaUpload): Response<User> {
        try {
            val response = UsersApi.service.registration(login, password, name)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return UsersApi.service.registration(login, password, name)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun authUser(login: String, password: String): Boolean {
        try {
            val response = UsersApi.service.authentication(login, password)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val authenticatedUser = response.body() ?: throw ApiError(response.code(), response.message())
            return authenticatedUser.login == login && authenticatedUser.password == password
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }*/

    override suspend fun updateUser(login: String, pass: String): Response<Token> {
        try {
            return UsersApi.service.updateUser(login, pass)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun registrationUser(
        login: String,
        pass: String,
        name: String,
        avatar: String?
    ): Response<ResponseBody> {
        try {
            val response = UsersApi.service.registrationUser(login, pass, name, avatar)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return UsersApi.service.registrationUser(login, pass, name, avatar)
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