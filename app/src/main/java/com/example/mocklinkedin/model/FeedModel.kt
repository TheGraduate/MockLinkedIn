package com.example.mocklinkedin.model

import com.example.mocklinkedin.dto.Post
data class FeedModel<T>(
    val objects: List<T> = emptyList(),
    val empty: Boolean = false,
)