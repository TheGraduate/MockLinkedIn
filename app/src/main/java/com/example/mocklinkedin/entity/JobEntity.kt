package com.example.mocklinkedin.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.Job
import java.util.Date

@Entity
data class JobEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val position: String,
    val start: Long,
    val finish: Long?,
    val link: String?,

) {
    fun toDto() = Job(
        id,
        name ,
        position,
        Date(start),
        finish?.let { Date(it) },
        link
    )

    companion object {
        fun fromDto(dto: Job) =
            JobEntity(
                dto.id,
                dto.name ,
                dto.position,
                dto.start.time,
                dto.finish?.time,
                dto.link
            )
    }
}

fun List<JobEntity>.toDto(): List<Job> = map(JobEntity::toDto)
fun List<Job>.toEntity(): List<JobEntity> = map(JobEntity::fromDto)