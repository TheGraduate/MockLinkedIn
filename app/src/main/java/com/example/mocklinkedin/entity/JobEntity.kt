package com.example.mocklinkedin.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mocklinkedin.dto.Job
import java.util.Date

@Entity
data class JobEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val company: String,
    val position: String,
    val workStart: Long,
    val workFinish: Long,

) {
    fun toDto() = Job(
        id,
        company ,
        position,
        Date(workStart),
        Date(workFinish)
    )

    companion object {
        fun fromDto(dto: Job) =
            JobEntity(
                dto.id,
                dto.company ,
                dto.position,
                dto.workStart.time,
                dto.workFinish.time
            )
    }
}

fun List<JobEntity>.toDto(): List<Job> = map(JobEntity::toDto)
fun List<Job>.toEntity(): List<JobEntity> = map(JobEntity::fromDto)