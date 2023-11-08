package com.example.mocklinkedin.dto

import com.example.mocklinkedin.enumeration.AttachmentType
import java.text.DateFormat
import java.util.Date

data class User (
    val id: Int,
    val nicName: String,
    val firstName: String,
    val lastName: String,
    val birthDate: Int,//DateFormat
    val registrationDate: Int,//DateFormat
    val avatar: Avatar? = null
)

data class Avatar(
    //val url: String,
    val type: AttachmentType,
)