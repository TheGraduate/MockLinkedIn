package com.example.mocklinkedin.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mocklinkedin.entity.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM JobEntity ORDER BY id DESC")
    fun getAll(): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(job: JobEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobs: List<JobEntity>)


  /*  @Query("UPDATE JobEntity SET company = :content WHERE id = :id")
    suspend fun updateContentById(id: Long, content: String)*/

    suspend fun save(job: JobEntity) =
        /*if (job.id == 0L)*/ insert(job)// else updateContentById(job.id)

    @Query("DELETE FROM JobEntity WHERE id = :id")
    suspend fun removeById(id: Long)

    @Query("SELECT COUNT(*) FROM JobEntity")
    suspend fun count(): Int
}



