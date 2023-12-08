package com.example.mocklinkedin.dao

import androidx.room.*
import com.example.mocklinkedin.entity.UserEntity
import kotlinx.coroutines.flow.Flow

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

        /*@Query("SELECT * FROM UserEntity WHERE login = :login AND password = :password")
        suspend fun authentication(login: String, password: String): UserEntity?*/

       /* @Transaction
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun registration(login: String, password: String, name: String)*/


    }
