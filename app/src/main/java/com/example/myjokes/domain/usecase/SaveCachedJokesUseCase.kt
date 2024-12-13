package com.example.myjokes.domain.usecase

import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.domain.repository.JokeRepository

class SaveCachedJokesUseCase(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(cachedJokes: List<Joke>) {
        jokeRepository.saveCachedJokes(cachedJokes)
    }
}