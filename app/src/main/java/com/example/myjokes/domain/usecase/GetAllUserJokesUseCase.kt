package com.example.myjokes.domain.usecase

import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.domain.repository.JokeRepository
import kotlinx.coroutines.flow.Flow

class GetAllUserJokesUseCase(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(): Flow<List<Joke>> {
        return jokeRepository.getAllUserJokes()
    }
}