package com.example.mocklinkedin.dto

import com.example.mocklinkedin.enumeration.AttachmentType
import java.util.Date

data class Post(
        val id: Long,
        val authorId: Long,
        val author: String,
        val authorAvatar: String?,
        val content: String,
        val published: Date,
        var likedByMe: Boolean,
        //var likes: Int?,
        //var shares: Int?,
        //var views: Int?,
        var coords: Geo?,
        val attachment: Attachment? = null,
)

data class Geo(
        val latitude: Double,
        val longitude: Double
)

data class Attachment(
        val url: String,
        val type: AttachmentType,
)