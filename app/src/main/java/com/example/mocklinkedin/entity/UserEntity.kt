package com.example.mocklinkedin.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.User

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val login: String,
    val userName: String,
    val password: String,
    val registrationDate: String,
    @Embedded
    var avatar: AttachmentEmbeddable?,
) {
    fun toDto() = User(
        id,
        login,
        userName,
        password,
        registrationDate,
        avatar?.toDto()
    )

    companion object {
        fun fromDto(dto: User) =
            UserEntity(
                dto.id,
                dto.login,
                dto.name,
                dto.password,
                dto.registrationDate,
                AttachmentEmbeddable.fromDto(dto.avatar))

    }
}

fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)