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
    val currentJokeIndex: LiveData<Int>
        get() = _currentJokeIndex

    fun generateJokes() {
        _jokes.value = JokeGenerator.generateJokeData()
    }

    fun showGeneratedData() {
        _jokes.value = JokeGenerator.data
    }

    fun setCurrentJokeIndex (index: Int) {
        _currentJokeIndex.value = index
    }
}