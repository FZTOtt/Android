package com.example.myjokes.ui.joke_details

import com.example.myjokes.data.JokeGenerator

class JokeDetailsPresenter(private val view: JokeDetailsView) {

    fun loadJokeDetails(jokePosition: Int) {
        if (jokePosition == -1) {
            view.showErrorAndCloseScreen("Шутка не найдена")
        } else {
            JokeGenerator.data[jokePosition].let {
                view.showJokeInfo(it)
            }
        }

    }
}