package com.example.mocklinkedin.api

import com.example.mocklinkedin.constants.Constants
import com.example.mocklinkedin.dto.Job
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private val okhttp = OkHttpClient.Builder()
    //.addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .client(okhttp)
    .build()

interface JobsApiService {
    @GET("jobs")
    suspend fun getAll(): Response<List<Job>>

    @GET("jobs/{id}")
    suspend fun getById(@Path("id") id: Long): Response<Job>

    @POST("jobs")
    suspend fun save(@Body job: Job): Response<Job>

    @DELETE("jobs/{id}")
    suspend fun removeById(@Path("id") id: Long): Response<Unit>

}

object JobsApi {
    val service: JobsApiService by lazy {
        retrofit.create(JobsApiService::class.java)
    }
}