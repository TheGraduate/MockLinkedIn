package com.example.mocklinkedin.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.Event
import java.util.Date

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String,
    val published: Long,
    var likedByMe: Boolean,
    //var likes: Int,
    //var shares: Int,
    //var views: Int,
    @Embedded
    var coords: GeoEmbeddable?,
    @Embedded
    var attachment: AttachmentEmbeddable?,
) {
    fun toDto() = Event(
        id,
        authorId,
        author,
        authorAvatar,
        content,
        Date(published),
        likedByMe,
        //likes = 0,
        //shares = 0,
        //views = 0,
        coords?.toDto(),
        attachment?.toDto()
    )

    companion object {
        fun fromDto(dto: Event) =
            EventEntity(
                dto.id,
                dto.authorId,
                dto.author,
                dto.authorAvatar,
                dto.content,
                dto.published.time,
                dto.likedByMe,
                //dto.likes,
                //dto.shares,
                //dto.views,
                GeoEmbeddable.fromDto(dto.coords),
                AttachmentEmbeddable.fromDto(dto.attachment))
    }
}

fun List<EventEntity>.toDto(): List<Event> = map(EventEntity::toDto)
fun List<Event>.toEntity(): List<EventEntity> = map(EventEntity::fromDto)