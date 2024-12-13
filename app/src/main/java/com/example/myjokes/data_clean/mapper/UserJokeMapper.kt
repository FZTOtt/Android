package com.example.myjokes.data_clean.mapper

import com.example.myjokes.data_clean.entity.UserJokeDbModel
import com.example.myjokes.domain.entity.Joke

class UserJokeMapper {
    fun toDomain(userJoke: UserJokeDbModel): Joke {
        return Joke(
            id = userJoke.id,
            category = userJoke.category,
            question = userJoke.question,
            answer = userJoke.answer,
            own = true
        )
    }

    fun fromDomain(joke: Joke): UserJokeDbModel {
        return UserJokeDbModel(
            id = joke.id,
            category = joke.category,
            question = joke.question,
            answer = joke.answer,
            own = true
        )
    }
}