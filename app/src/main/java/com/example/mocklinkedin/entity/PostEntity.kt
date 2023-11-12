package com.example.mocklinkedin.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.Attachment
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.enumeration.AttachmentType

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Int = 0,
    var shares: Int = 0,
    var views: Int = 0,
    @Embedded
    var attachment: AttachmentEmbeddable?,
) {
    fun toDto() = Post(id, author,  content, published, likedByMe, likes, shares, views, attachment?.toDto())

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id,
                dto.author,
                dto.content,
                dto.published,
                dto.likedByMe,
                dto.likes,
                dto.shares,
                dto.views,
                AttachmentEmbeddable.fromDto(dto.attachment))

    }
}

data class AttachmentEmbeddable(
    var url: String,
    var type: AttachmentType,
) {
    fun toDto() = Attachment(url, type)

    companion object {
        fun fromDto(dto: Attachment?) = dto?.let {
            AttachmentEmbeddable(it.url, it.type)
        }
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(): List<PostEntity> = map(PostEntity::fromDto)