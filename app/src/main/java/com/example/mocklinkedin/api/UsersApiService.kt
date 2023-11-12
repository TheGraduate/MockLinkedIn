package com.example.mocklinkedin.api

import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.dto.User
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

private const val BASE_URL = "https://netomedia.ru" //"${BuildConfig.BASE_URL}/api/slow/"

/*private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}*/

private val okhttp = OkHttpClient.Builder()
    //.addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okhttp)
    .build()

interface UsersApiService {
    @GET("users")
    suspend fun getAll(): Response<List<User>>

    @GET("users/{id}")
    suspend fun getById(@Path("id") id: Long): Response<User>

    @POST("users/authentication")
    suspend fun authentication(@Body login: String, password: String): Response<User>

    @POST("users/registration")
    suspend fun registration(@Body login: String, password: String, name: String): Response<User>

    @Multipart
    @POST("media")
    suspend fun  upload(@Part media: MultipartBody.Part): Response<Media>
}

object UsersApi {
    val service: UsersApiService by lazy {
        retrofit.create(UsersApiService::class.java)
    }
}