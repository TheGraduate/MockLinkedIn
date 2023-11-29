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
    var username: String,
    val password: String,
    //val registrationDate: Long,
    @Embedded
    var avatar: AttachmentEmbeddable?,
) {
    fun toDto() = User(
        id,
        login,
        username = "Username",
        password,
        //registrationDate,
        avatar?.toDto()
    )

    companion object {
        fun fromDto(dto: User) =
            UserEntity(
                dto.id,
                dto.login,
                dto.username,
                dto.password,
                //dto.registrationDate,
                AttachmentEmbeddable.fromDto(dto.avatar))

    }
}

fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)