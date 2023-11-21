package com.example.mocklinkedin.dto

import java.util.Date

data class Event (
    val id: Long,
    val author: String,
    val content: String,
    val published: Date = Date(),
    var likedByMe: Boolean,
    var likes: Int,
    var shares: Int,
    var views: Int,
    var geo: Geo?,
    val attachment: Attachment? = null,
)
