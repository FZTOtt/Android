package com.example.myjokes.data_clean.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myjokes.data_clean.entity.CachedJokeDbModel
import com.example.myjokes.data_clean.entity.UserJokeDbModel

@Database(entities = [UserJokeDbModel::class, CachedJokeDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object{
        @Volatile
        lateinit var INSTANCE: AppDatabase

        fun initDatabase(context: Context) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
            INSTANCE = instance
        }
    }
}