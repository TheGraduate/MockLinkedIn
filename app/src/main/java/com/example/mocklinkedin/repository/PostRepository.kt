package com.example.mocklinkedin.repository

import androidx.lifecycle.LiveData
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)
    fun saveWithAttachment(post: Post, upload: MediaUpload)
    //fun upload(upload: MediaUpload): Media

}