package com.example.mocklinkedin.dto

import com.example.mocklinkedin.enumeration.AttachmentType

data class Post (
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        var likedByMe: Boolean,
        var likes: Int,
        var shares: Int,
        var views: Int,
        val attachment: Attachment? = null,
)
data class Attachment(
        //val url: String,
        val type: AttachmentType,
)