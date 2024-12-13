package com.example.myjokes.data_clean.mapper

import com.example.myjokes.data_clean.entity.CachedJokeDbModel
import com.example.myjokes.domain.entity.Joke

class CachedJokeMapper {

    fun toDomain(cachedJoke: CachedJokeDbModel): Joke {
        return Joke(
            id = cachedJoke.id,
            category = cachedJoke.category + " (Cached)",
            question = cachedJoke.question,
            answer = cachedJoke.answer
        )
    }

    fun fromDomain(joke: Joke): CachedJokeDbModel {
        return CachedJokeDbModel(
            id = joke.id,
            category = joke.category,
            question = joke.question,
            answer = joke.answer,
            timestamp = System.currentTimeMillis()
        )
    }
}