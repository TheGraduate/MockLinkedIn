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
    val authorId: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val ownedByMe: Boolean = false,
    //val likes: Int = 0,
    //var shares: Int = 0,
    //var views: Int = 0,
    //val latitude: Double = 0.0,
    //val longitude: Double = 0.0,
    @Embedded
    var coords: GeoEmbeddable?,
    @Embedded
    var attachment: AttachmentEmbeddable?,
) {
    fun toDto() = Post(
        id,
        authorId ,
        author,
        authorAvatar,
        content,
        Date(published),
        likedByMe,
        ownedByMe,
        //likes = 0,
        //shares = 0,
        //views = 0,
        coords?.toDto(),
        attachment?.toDto()
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.authorId,
                dto.author,
                dto.authorAvatar,
                dto.content,
                dto.published.time ,
                dto.likedByMe,
                dto.ownedByMe,
                //dto.likes,
                //dto.shares,
                //dto.views,
                GeoEmbeddable.fromDto(dto.coords),
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