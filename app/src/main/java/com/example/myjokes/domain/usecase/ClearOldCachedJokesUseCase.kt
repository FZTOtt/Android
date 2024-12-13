package com.example.myjokes.domain.usecase

import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.domain.repository.JokeRepository

class ClearOldCachedJokesUseCase(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke() {
        return jokeRepository.clearOldCachedJokes()
    }
}