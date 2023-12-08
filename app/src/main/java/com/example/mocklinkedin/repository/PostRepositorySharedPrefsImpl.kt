package com.example.mocklinkedin.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mocklinkedin.api.PostsApi
import com.example.mocklinkedin.enumeration.AttachmentType
import com.example.mocklinkedin.dto.Attachment
import com.example.mocklinkedin.dto.Media
import com.example.mocklinkedin.dto.MediaUpload
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.mocklinkedin.dto.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.IOException

/*
class PostRepositorySharedPrefsImpl(
    context: Context,
) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("rep", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 0L//1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, type)
            nextId = posts.maxByOrNull { it.id }?.id?.plus(1) ?: 1L
            data.value = posts
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }
    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
        sync()
    }
    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun saveWithAttachment(post: Post, upload: MediaUpload) {
        //val media = upload(upload)
        val postWithAttachment = post.copy(attachment = Attachment(*/
/*media.id,*//*
 AttachmentType.IMAGE))
        save(postWithAttachment)
    }

    override  fun upload(upload: MediaUpload): Media {
            val media = upload.file.name
                MultipartBody.Part.createFormData(
                "file", upload.file.name, upload.file.asRequestBody()
            )
        val response = PostsApi.service.upload(media)

            return response.body()
    }

*/
/*    override fun upload(upload: MediaUpload): Media {
        val media = MultipartBody.Part.createFormData(
            "file", upload.file.name, upload.file.asRequestBody()
        )

    }*//*


    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}*/
