package com.example.myjokes.data

import com.example.myjokes.data.db.CachedJoke
import com.example.myjokes.data.db.JokeDao
import com.example.myjokes.data.db.UserJoke
import kotlinx.coroutines.flow.Flow

class JokeRepository(private val jokeDao: JokeDao) {

    fun getAllUserJokes(): Flow<List<UserJoke>> = jokeDao.getAllUserJokes()
    fun getAllCachedJokes(): Flow<List<CachedJoke>> = jokeDao.getAllCachedJokes()

    suspend fun addUserJoke(joke: UserJoke) {
        jokeDao.insertUserJoke(joke)
    }

    suspend fun refreshCachedJokes() {
        val netJokes = RetrofitInstance.api.getRandomJokes()
        val cachedJokes = netJokes.jokes.map { joke ->
            CachedJoke(joke.id, joke.category, joke.question, joke.answer, System.currentTimeMillis())
        }
        jokeDao.insertCachedJokes(cachedJokes)
    }

    suspend fun clearOldCachedJokes() {
        val timestamp = System.currentTimeMillis() - 24 * 60 * 60 * 1000
        jokeDao.clearOldCachedJokes(timestamp)
    }

    suspend fun saveCachedJokes(cachedJokes: List<CachedJoke>) {
        jokeDao.insertCachedJokes(cachedJokes)
    }
}