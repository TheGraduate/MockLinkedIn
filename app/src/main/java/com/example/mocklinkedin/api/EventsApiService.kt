package com.example.mocklinkedin.api

import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.Geo
import com.example.mocklinkedin.dto.Media
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

interface EventsApiService {
    @GET("events")
    suspend fun getAll(): Response<List<Event>>

    @GET("events/{id}")
    suspend fun getById(@Path("id") id: Long): Response<Event>

    @POST("events")
    suspend fun save(@Body event: Event, location: Geo, published: Long): Response<Event>

    @DELETE("events/{id}")
    suspend fun removeById(@Path("id") id: Long): Response<Unit>

    @POST("events/{id}/likes")
    suspend fun likeById(@Path("id") id: Long): Response<Event>

    @DELETE("events/{id}/likes")
    suspend fun unlikeById(@Path("id") id: Long): Response<Event>

    @POST("events/{id}/shares")
    suspend fun shareById(@Path("id") id: Long): Response<Event>

    @GET("events/{id}/newer")
    suspend fun getNewer(@Path("id") id: Long): Response<List<Event>>

    @GET("events")
    suspend fun showAll(): Response<List<Event>>

    @Multipart
    @POST("media")
    suspend fun  upload(@Part media: MultipartBody.Part): Response<Media>

}

object EventsApi {
    val service: EventsApiService by lazy {
        retrofit.create(EventsApiService::class.java)
    }
}