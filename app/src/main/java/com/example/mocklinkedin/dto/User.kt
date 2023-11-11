package com.example.mocklinkedin.dto

import com.example.mocklinkedin.enumeration.AttachmentType
import java.text.DateFormat
import java.util.Date

data class User (
    val id: Long,
    val nickName: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val birthDate: Int,//DateFormat
    val registrationDate: String,//DateFormat
    val avatar: Avatar? = null
)

data class Avatar(
    //val url: String,
    val type: AttachmentType,
)