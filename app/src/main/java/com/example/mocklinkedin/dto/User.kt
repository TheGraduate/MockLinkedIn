package com.example.mocklinkedin.dto

import com.example.mocklinkedin.enumeration.AttachmentType
import java.util.Date

data class User(
    val id: Long,
    val login: String,
    val name: String,
    val password: String,
    val registrationDate: Long,
    val avatar: Attachment? = null
)

data class Avatar(
    val url: String,
    val type: AttachmentType,
)