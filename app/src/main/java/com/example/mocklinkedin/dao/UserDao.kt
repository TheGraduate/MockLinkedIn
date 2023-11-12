package com.example.mocklinkedin.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mocklinkedin.dto.User
import com.example.mocklinkedin.entity.EventEntity
import com.example.mocklinkedin.entity.PostEntity
import com.example.mocklinkedin.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity ORDER BY id DESC")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getById(id: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserEntity>)

    @Query("SELECT * FROM UserEntity WHERE nickName = :login AND password = :password")
    suspend fun authentication(login: String, password: String): User?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registration(login: String, password: String, name: String): User?


}