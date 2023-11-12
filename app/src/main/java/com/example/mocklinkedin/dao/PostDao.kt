package com.example.mocklinkedin.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.mocklinkedin.entity.PostEntity
import com.example.mocklinkedin.enumeration.AttachmentType

@Dao
    interface PostDao {
        @Query("SELECT * FROM PostEntity ORDER BY id DESC")
        fun getAll(): Flow<List<PostEntity>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(post: PostEntity)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(posts: List<PostEntity>)


        @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
        suspend fun updateContentById(id: Long, content: String)

        suspend fun save(post: PostEntity) =
            if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

        @Query("""
            UPDATE PostEntity SET
            likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
            likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
            WHERE id = :id
            """)
        suspend fun likeById(id: Long)

        @Query("DELETE FROM PostEntity WHERE id = :id")
        suspend fun removeById(id: Long)

        @Query("""
            UPDATE PostEntity SET
            shares = shares + 1
            WHERE id = :id
            """)
        suspend fun onShare (id: Long)

        @Query("SELECT COUNT(*) FROM PostEntity")
        suspend fun count(): Int

    }

    class Converter {
        @TypeConverter
        fun toAttachmentType(value: String) = enumValueOf<AttachmentType>(value)
        @TypeConverter
        fun fromAttachmentType(value: AttachmentType) = value.name
    }