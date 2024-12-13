package com.example.myjokes.data_clean.repository

import JokesApiResponse
import com.example.myjokes.data_clean.datasource.local.LocalDataSource
import com.example.myjokes.data_clean.datasource.remote.RemoteDataSource
import com.example.myjokes.data_clean.entity.CachedJokeDbModel
import com.example.myjokes.data_clean.entity.UserJokeDbModel
import com.example.myjokes.data_clean.mapper.CachedJokeMapper
import com.example.myjokes.data_clean.mapper.JokeDtoMapper
import com.example.myjokes.data_clean.mapper.UserJokeMapper
import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.domain.repository.JokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JokeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val cachedJokeMapper: CachedJokeMapper,
    private val userJokeMapper: UserJokeMapper,
    private val jokeDtoMapper: JokeDtoMapper
): JokeRepository {
    override fun getAllUserJokes(): Flow<List<Joke>> {
        return localDataSource.getAllUserJokes().map { userJokes ->
            userJokes.map { userJoke ->
                userJokeMapper.toDomain(userJoke)
            }
        }
    }

    override fun getAllCachedJokes(): Flow<List<Joke>> {
        return localDataSource.getAllCachedJokes().map { cachedJokes ->
            cachedJokes.map { cachedJoke ->
                cachedJokeMapper.toDomain(cachedJoke)
            }
        }
    }

    override suspend fun addUserJoke(joke: Joke) {
        val userJoke = userJokeMapper.fromDomain(joke)
        localDataSource.insertUserJoke(userJoke)
    }

    override suspend fun clearOldCachedJokes() {
        val timestamp = System.currentTimeMillis() - 24 * 60 * 60 * 1000
        return localDataSource.clearOldCachedJokes(timestamp)
    }

    override suspend fun saveCachedJokes(cachedJokes: List<Joke>) {
        val jokes = cachedJokes.map { joke ->
            cachedJokeMapper.fromDomain(joke)
        }
        localDataSource.insertCachedJokes(jokes)
    }

    override suspend fun getJokesFromNet(): List<Joke> {
        val response = remoteDataSource.getRandomJokes()
        return response.jokes.map { jokeDTO ->
            jokeDtoMapper.toDomain(jokeDTO)
        }
    }
}