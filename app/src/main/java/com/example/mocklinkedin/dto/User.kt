package com.example.mocklinkedin.dto

import com.example.mocklinkedin.enumeration.AttachmentType

data class User(
    val id: Long,
    val login: String,
    var username: String,
    val password: String,
    //val registrationDate: Long,
    val avatar: Attachment? = null
)

data class Avatar(
    val url: String,
    val type: AttachmentType,
)