package com.example.myjokes.ui.joke_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjokes.data.Joke
import com.example.myjokes.data.JokeGenerator

class JokeListViewModel: ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _currentJokeIndex = MutableLiveData<Int>()

    private val _currentJoke = MutableLiveData<Joke>()
    val currentJoke: LiveData<Joke> = _currentJoke

    fun generateJokes() {
        _jokes.value = JokeGenerator.generateJokeData()
    }

    fun setCurrentJokeIndex (index: Int) {
        _currentJokeIndex.value = index
        updateCurrentJoke()
    }

    private fun updateCurrentJoke() {
        val jokesList = _jokes.value
        val index = _currentJokeIndex.value ?: 0
        if (jokesList != null && index in jokesList.indices) {
            _currentJoke.value = jokesList[index]
        } else {
            _currentJoke.value = Joke(-1, "error", "error", "error")
        }
    }
}