package com.example.mocklinkedin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mocklinkedin.dao.EventDao
import com.example.mocklinkedin.dao.PostDao
import com.example.mocklinkedin.dao.UserDao
import com.example.mocklinkedin.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
/*@TypeConverters(Converters::class)*/ //TODO
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao

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