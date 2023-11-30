package com.example.mocklinkedin.dto

import java.util.Date

data class Event (
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
