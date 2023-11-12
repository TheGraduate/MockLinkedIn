package com.example.mocklinkedin.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.User

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nickName: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val registrationDate: String,
    @Embedded
    var avatar: AttachmentEmbeddable?,
) {
    fun toDto() = User(id, nickName,  firstName, lastName, password, registrationDate, avatar?.toDto())

    companion object {
        fun fromDto(dto: User) =
            UserEntity(
                dto.id,
                dto.nickName,
                dto.firstName,
                dto.lastName,
                dto.password,
                dto.registrationDate,
                AttachmentEmbeddable.fromDto(dto.avatar))

    }
}


fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)