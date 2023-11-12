package com.example.mocklinkedin.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import com.example.mocklinkedin.entity.EventEntity
import com.example.mocklinkedin.entity.PostEntity
import com.example.mocklinkedin.enumeration.AttachmentType
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntity ORDER BY id DESC")
    fun getAll(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(events: List<EventEntity>)


    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    suspend fun updateContentById(id: Long, content: String)

    suspend fun save(event: EventEntity) =
        if (event.id == 0L) insert(event) else updateContentById(event.id, event.content)

    @Query("""
            UPDATE EventEntity SET
            likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
            likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
            WHERE id = :id
            """)
    suspend fun likeById(id: Long)

    @Query("DELETE FROM EventEntity WHERE id = :id")
    suspend fun removeById(id: Long)

    @Query("""
            UPDATE EventEntity SET
            shares = shares + 1
            WHERE id = :id
            """)
    suspend fun onShare (id: Long)

    @Query("SELECT COUNT(*) FROM EventEntity")
    suspend fun count(): Int

}