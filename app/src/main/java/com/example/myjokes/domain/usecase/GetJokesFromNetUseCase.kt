package com.example.myjokes.domain.usecase

import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.domain.repository.JokeRepository

class GetJokesFromNetUseCase(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(): List<Joke> {
        return jokeRepository.getJokesFromNet()
    }
}