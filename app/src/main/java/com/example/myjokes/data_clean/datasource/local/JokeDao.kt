package com.example.myjokes.data_clean.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myjokes.data_clean.entity.CachedJokeDbModel
import com.example.myjokes.data_clean.entity.UserJokeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserJoke(joke: UserJokeDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedJokes(jokes: List<CachedJokeDbModel>)

    @Query("SELECT * FROM user_jokes")
    fun getAllUserJokes(): Flow<List<UserJokeDbModel>>

    @Query("SELECT * FROM cached_jokes")
    fun getAllCachedJokes(): Flow<List<CachedJokeDbModel>>

    @Query("DELETE FROM cached_jokes WHERE timestamp < :timestamp")
    suspend fun clearOldCachedJokes(timestamp: Long)
}