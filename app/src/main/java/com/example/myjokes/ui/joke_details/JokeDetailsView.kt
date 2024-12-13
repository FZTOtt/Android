package com.example.myjokes.ui.joke_details

import com.example.myjokes.domain.entity.Joke

interface JokeDetailsView {

    fun showJokeInfo(joke: Joke)

    fun showErrorAndCloseScreen(errorMessage: String)
}