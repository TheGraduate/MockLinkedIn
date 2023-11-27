package com.example.mocklinkedin.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.Attachment
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.enumeration.AttachmentType
import java.util.Date

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: Long,
    var likedByMe: Boolean,
    var likes: Int,
    var shares: Int,
    var views: Int,
    @Embedded
    var geo: GeoEmbeddable?,
    @Embedded
    var attachment: AttachmentEmbeddable?,
) {
    fun toDto() = Event(
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
        fun fromDto(dto: Event) =
            EventEntity(
                dto.id,
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

fun List<EventEntity>.toDto(): List<Event> = map(EventEntity::toDto)
fun List<Event>.toEntity(): List<EventEntity> = map(EventEntity::fromDto)