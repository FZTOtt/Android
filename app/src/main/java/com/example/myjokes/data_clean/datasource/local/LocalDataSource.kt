package com.example.myjokes.data_clean.datasource.local

import com.example.myjokes.data_clean.entity.CachedJokeDbModel
import com.example.myjokes.data_clean.entity.UserJokeDbModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertUserJoke(joke: UserJokeDbModel)
    suspend fun insertCachedJokes(jokes: List<CachedJokeDbModel>)
    fun getAllUserJokes(): Flow<List<UserJokeDbModel>>
    fun getAllCachedJokes(): Flow<List<CachedJokeDbModel>>
    suspend fun clearOldCachedJokes(timestamp: Long)

}