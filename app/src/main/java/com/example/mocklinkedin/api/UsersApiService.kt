package com.example.mocklinkedin.api

import com.example.mocklinkedin.auth.AppAuth
import com.example.mocklinkedin.constants.Constants.Companion.BASE_URL
import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.Token
import com.example.mocklinkedin.dto.User
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

//private const val BASE_URL = "https://netomedia.ru" //"${BuildConfig.BASE_URL}/api/slow/"

val logging = HttpLoggingInterceptor().apply {
    //if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    //}
}

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(logging)
    .addInterceptor { chain ->
        AppAuth.getInstance().authStateFlow.value.token?.let { token ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", token)
                .build()
            return@addInterceptor chain.proceed(newRequest)
        }
        chain.proceed(chain.request())
    }
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

   /* @POST("users/authentication")
    suspend fun authentication(@Body login: String, password: String): Response<User>

    @POST("users/registration")
    suspend fun registration(@Body login: String, password: String, name: String): Response<User>*/

    @Multipart
    @POST("media")
    suspend fun  upload(@Part media: MultipartBody.Part): Response<Media>

    @FormUrlEncoded
    @POST("users/authentication")
    suspend fun updateUser(
        @Field("login") login: String,
        @Field("password") pass: String
    ): Response<Token>

    @FormUrlEncoded
    @POST("users/registration")
    suspend fun registrationUser(
        @Field("login") login: String,
        @Field("password") pass: String,
        @Field("name") name: String,
        @Field("file") avatar: String?
    ): Response<ResponseBody>
}

object UsersApi {
    val service: UsersApiService by lazy {
        retrofit.create(UsersApiService::class.java)
    }
}