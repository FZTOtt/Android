package com.example.myjokes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myjokes.data.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserJoke(joke: UserJoke)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedJokes(jokes: List<CachedJoke>)

    @Query("SELECT * FROM user_jokes")
    fun getAllUserJokes(): Flow<List<UserJoke>>

    @Query("SELECT * FROM cached_jokes")
    fun getAllCachedJokes(): Flow<List<CachedJoke>>

    @Query("DELETE FROM cached_jokes WHERE timestamp < :timestamp")
    suspend fun clearOldCachedJokes(timestamp: Long)
}