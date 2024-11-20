package com.example.myjokes.ui.joke_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjokes.data.Joke
import com.example.myjokes.data.JokeGenerator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JokeListViewModel: ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _currentJokeIndex = MutableLiveData<Int>()

    private val _currentJoke = MutableLiveData<Joke>()
    val currentJoke: LiveData<Joke> = _currentJoke

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun generateJokes() {
        _jokes.value = JokeGenerator.generateJokeData()
        print("Обновили joke")
    }

    fun getJokes() {
        _isLoading.value = true  // Показать индикатор загрузки

        viewModelScope.launch {
            delay(2000)  // Искусственная задержка 2 секунды
            _jokes.value = JokeGenerator.generateJokeData()  // Загрузка данных
            _isLoading.value = false  // Скрыть индикатор загрузки
        }
    }

    fun addJoke(category: String, question: String, answer: String) {
        val lastIndex = _jokes.value?.size ?: -1
        val index = lastIndex + 1
        val joke = Joke(index, category, question, answer)
        print("Шутка ${category}, ${question}, ${answer}")
        val currentJokes = _jokes.value ?: emptyList()
        _jokes.value = currentJokes + joke
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