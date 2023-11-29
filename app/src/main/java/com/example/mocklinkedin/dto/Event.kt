package com.example.mocklinkedin.dto

data class Event (
    val id: Long,
    val authorId: Long,
    val author: String,
    val content: String,
    val published: Long,
    var likedByMe: Boolean,
    var likes: Int?,
    var shares: Int?,
    var views: Int?,
    var coords: Geo?,
    val attachment: Attachment? = null,
)
