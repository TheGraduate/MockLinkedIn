package com.example.mocklinkedin.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.Attachment
import com.example.mocklinkedin.dto.Geo
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.enumeration.AttachmentType
import java.util.Date

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0,
    var shares: Int = 0,
    var views: Int = 0,
    //val latitude: Double = 0.0,
    //val longitude: Double = 0.0,
    @Embedded
    var geo: GeoEmbeddable?,
    @Embedded
    var attachment: AttachmentEmbeddable?,
) {
    fun toDto() = Post(
        id,
        author,
        content,
        published,
        likedByMe,
        likes,
        shares,
        views,
        geo?.toDto(),
        attachment?.toDto()
    )

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
                GeoEmbeddable.fromDto(dto.geo),
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

data class GeoEmbeddable(
    var latitude: Double,
    var longitude: Double,
) {
    fun toDto() = Geo(latitude, longitude)

    companion object {
        fun fromDto(dto: Geo?) = dto?.let {
            GeoEmbeddable(it.latitude, it.longitude)
        }
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(): List<PostEntity> = map(PostEntity::fromDto)