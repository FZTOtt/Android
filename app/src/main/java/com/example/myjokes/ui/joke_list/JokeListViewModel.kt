package com.example.myjokes.ui.joke_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjokes.data.Joke
import com.example.myjokes.data.JokeGenerator
import com.example.myjokes.data.RetrofitInstance
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

    private var customJokeIdCounter = 0
    private val customJokes = mutableListOf<Joke>()
    private val loadedJokes = mutableListOf<Joke>()

    private var isLoadingMore = false

    fun generateJokes() {
        if (customJokes.size == 0) {
            customJokes.addAll(JokeGenerator.generateJokeData())
            getJokes()
        }
    }

    fun getJokes(refresh: Boolean = true) {
        _isLoading.value = true
        if (isLoadingMore) return
        if (!refresh) {
            isLoadingMore = true
        }
        println("add new")
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomJokes()
                if (!response.error) {
                    val jokes = response.jokes
                    if (refresh) {
                        loadedJokes.clear()
                    }
                    loadedJokes.addAll(jokes.map { joke ->
                        Joke(joke.id, joke.category, joke.question, joke.answer)
                    })
                    updateJokesList()
                } else {
                    println("запрос не удался")
                }
            } catch (e: Exception) {
                println(e)
            } finally {
                isLoadingMore = false
                _isLoading.value = false
            }
        }
    }

    fun addJoke(category: String, question: String, answer: String, own: Boolean = false) {
        val joke = Joke(customJokeIdCounter++, category, question, answer, own)
        customJokes.add(joke)
        updateJokesList()
    }

    private fun updateJokesList() {
        _jokes.value = customJokes + loadedJokes
    }

//    fun addJoke(category: String, question: String, answer: String) {
//        val lastIndex = _jokes.value?.size ?: -1
//        val index = lastIndex + 1
//        val joke = Joke(index, category, question, answer)
//        print("Шутка ${category}, ${question}, ${answer}")
//        val currentJokes = _jokes.value ?: emptyList()
//        _jokes.value = currentJokes + joke
//    }

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