package com.example.myjokes.data_clean.datasource.local

import com.example.myjokes.data_clean.entity.CachedJokeDbModel
import com.example.myjokes.data_clean.entity.UserJokeDbModel
import kotlinx.coroutines.flow.Flow


class LocalDataSourceImpl(
    private val dao: JokeDao
): LocalDataSource {
    override suspend fun insertUserJoke(joke: UserJokeDbModel) {
        return dao.insertUserJoke(joke)
    }

    override suspend fun insertCachedJokes(jokes: List<CachedJokeDbModel>) {
        return dao.insertCachedJokes(jokes)
    }

    override fun getAllUserJokes(): Flow<List<UserJokeDbModel>> {
        return dao.getAllUserJokes()
    }

    override fun getAllCachedJokes(): Flow<List<CachedJokeDbModel>> {
        return dao.getAllCachedJokes()
    }

    override suspend fun clearOldCachedJokes(timestamp: Long) {
        return dao.clearOldCachedJokes(timestamp)
    }

}