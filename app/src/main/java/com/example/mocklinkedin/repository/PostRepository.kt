package com.example.mocklinkedin.repository

import androidx.lifecycle.LiveData
import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    val data: Flow<List<Post>>
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun getAll()

    suspend fun likeById(id: Long)
    suspend fun unlikeById(id: Long)
    suspend fun shareById(id: Long)
    suspend fun save(post: Post)
    suspend fun removeById(id: Long)
    suspend fun saveWithAttachment(post: Post, upload: MediaUpload)
    suspend fun upload(upload: MediaUpload): Media

}