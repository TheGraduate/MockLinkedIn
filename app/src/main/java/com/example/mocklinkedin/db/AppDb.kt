package com.example.mocklinkedin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mocklinkedin.dao.Converters
import com.example.mocklinkedin.dao.EventDao
import com.example.mocklinkedin.dao.JobDao
import com.example.mocklinkedin.dao.PostDao
import com.example.mocklinkedin.dao.UserDao
import com.example.mocklinkedin.entity.EventEntity
import com.example.mocklinkedin.entity.JobEntity
import com.example.mocklinkedin.entity.PostEntity
import com.example.mocklinkedin.entity.UserEntity

@Database(
    entities = [
        PostEntity::class,
        UserEntity::class,
        EventEntity::class,
        JobEntity::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun jobDao(): JobDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}