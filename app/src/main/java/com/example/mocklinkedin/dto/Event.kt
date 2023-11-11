package com.example.mocklinkedin.dto
data class Event (
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
