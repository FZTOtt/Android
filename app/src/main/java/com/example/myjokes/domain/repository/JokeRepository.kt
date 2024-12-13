package com.example.myjokes.domain.repository

import JokesApiResponse
import com.example.myjokes.domain.entity.Joke
import kotlinx.coroutines.flow.Flow

interface JokeRepository {
    fun getAllUserJokes(): Flow<List<Joke>>
    fun getAllCachedJokes(): Flow<List<Joke>>
    suspend fun addUserJoke(joke: Joke)
    suspend fun clearOldCachedJokes()
    suspend fun saveCachedJokes(cachedJokes: List<Joke>)
    suspend fun getJokesFromNet(): List<Joke>
}