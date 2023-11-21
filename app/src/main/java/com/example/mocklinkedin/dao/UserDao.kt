package com.example.mocklinkedin.dao

import androidx.room.*
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mocklinkedin.entity.EventEntity
import com.example.mocklinkedin.entity.PostEntity
import com.example.mocklinkedin.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.mocklinkedin.dao.UserDao
import com.example.mocklinkedin.dto.User

@Dao
    interface UserDao {
        @Query("SELECT * FROM UserEntity ORDER BY id DESC")
        fun getAll(): Flow<List<UserEntity>>

        @Query("SELECT * FROM UserEntity WHERE id = :id")
        suspend fun getById(id: Long): UserEntity?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(user: UserEntity)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(users: List<UserEntity>)

        @Query("SELECT * FROM UserEntity WHERE login = :login AND password = :password")
        suspend fun authentication(login: String, password: String): UserEntity?

        //@Transaction
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun registration(login: String, password: String, name: String)


    }
