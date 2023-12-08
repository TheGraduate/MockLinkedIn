package com.example.mocklinkedin.dto

import com.example.mocklinkedin.enumeration.AttachmentType

data class User(
    val id: Long,
    val login: String,
    var name: String,
    //val password: String,
    //val registrationDate: Long,
    val avatar: String?
)

data class Avatar(
    val url: String,
    val type: AttachmentType,
)