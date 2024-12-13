package com.example.myjokes.data_clean.mapper

import com.example.myjokes.data_clean.entity.JokeDTO
import com.example.myjokes.domain.entity.Joke

class JokeDtoMapper {
    fun toDomain(jokeDTO: JokeDTO): Joke {
        return Joke(
            id = jokeDTO.id,
            category = jokeDTO.category,
            question = jokeDTO.question,
            answer = jokeDTO.answer
        )
    }
}