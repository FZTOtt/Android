package com.example.myjokes.ui.joke_details

import com.example.myjokes.data.JokeGenerator

class JokeDetailsPresenter(private val view: JokeDetailsView) {

    fun loadJokeDetails(jokePosition: Int) {
        val joke = JokeGenerator.data.getOrNull(jokePosition)
        if (joke == null) {
            view.showErrorAndCloseScreen("Шутка не найдена")
        } else {
            JokeGenerator.data[jokePosition].let {
                view.showJokeInfo(it)
            }
        }
    }
}