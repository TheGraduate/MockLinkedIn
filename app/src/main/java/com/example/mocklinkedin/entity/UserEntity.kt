package com.example.mocklinkedin.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.User

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val login: String,
    var name: String,
    //val password: String?,
    var avatar: String?,
) {
    fun toDto() = User(
        id,
        login,
        name = "Username",
        //password,
        avatar
    )

    companion object {
        fun fromDto(dto: User) =
            UserEntity(
                dto.id,
                dto.login,
                dto.name,
                //dto.password,
                dto.avatar
            )
    }
}

fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)