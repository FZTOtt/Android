package com.example.myjokes.ui.joke_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjokes.data.ErrorType
import com.example.myjokes.data.Joke
import com.example.myjokes.data.JokeRepository
import com.example.myjokes.data.RetrofitInstance
import com.example.myjokes.data.db.AppDatabase
import com.example.myjokes.data.db.CachedJoke
import com.example.myjokes.data.db.UserJoke
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class JokeListViewModel: ViewModel() {

    private val _jokesFlow = MutableStateFlow<List<Joke>>(emptyList())
    val jokesFlow: StateFlow<List<Joke>> get() = _jokesFlow

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _error = MutableLiveData<ErrorType>(ErrorType.NONE)
    val error: LiveData<ErrorType> = _error

    private val _currentJokeIndex = MutableLiveData<Int>()

    private val _currentJoke = MutableLiveData<Joke>()
    val currentJoke: LiveData<Joke> = _currentJoke

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isLoadingMore = false

    private val repository: JokeRepository by lazy {
        JokeRepository(
            AppDatabase.INSTANCE.jokeDao()
        )
    }

    fun loadAllJokes() {
        println("load all jokes")

        refreshJokes()
        println(_jokes.value)
        getJokes()
        println(_jokes.value)
        viewModelScope.launch {
            _isLoading.value = true
            combine(repository.getAllUserJokes(), repository.getAllCachedJokes()) {
                userJokes, cachedJokes ->

                val jokes = mutableListOf<Joke>()

                jokes.addAll(userJokes.map {
                    Joke(
                        id = it.id,
                        category = it.category,
                        question = it.question,
                        answer = it.answer,
                        own = true
                    )
                })
                if (_error.value == ErrorType.CONNECTION_ERROR) {
                    jokes.addAll(cachedJokes.map {
                        Joke(
                            id = it.id,
                            category = it.category,
                            question = it.question,
                            answer = it.answer,
                            own = false
                        )
                    })
                } else {
                    println("из сети")
                    println(_jokes.value)
                    _jokes.value?.let {
                        jokes.addAll(it.map {
                            Joke(
                                id = it.id,
                                category = it.category,
                                question = it.question,
                                answer = it.answer,
                                own = true
                            )
                        })
                    }
                }
                jokes
            }.collect { combinedJokes ->
                _jokesFlow.value = combinedJokes
                println("после добавления шуток")
                println(_jokesFlow.value)
                _isLoading.value = false
            }
        }
        println("after loal all")
        println(_jokesFlow.value)
    }

    private fun refreshJokes() {
        viewModelScope.launch {
            repository.clearOldCachedJokes()
//            repository.refreshCachedJokes()
//            loadAllJokes()
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
            val loadedJokes = mutableListOf<Joke>()
            try {
                val response = RetrofitInstance.api.getRandomJokes()
                if (!response.error) {
                    val jokes = response.jokes
                    loadedJokes.addAll(jokes.map { joke ->
                        Joke(joke.id, joke.category, joke.question, joke.answer)
                    })
                    println("loaded jokes")
                    println(loadedJokes)

                    repository.saveCachedJokes(loadedJokes.map { joke ->
                        CachedJoke(
                            id = joke.id,
                            category = joke.category,
                            question = joke.question,
                            answer = joke.answer,
                            timestamp = System.currentTimeMillis()
                        )
                    })
                    if (_error.value == ErrorType.CONNECTION_ERROR) {
                        _error.value = ErrorType.NONE
                    }
                }
            } catch (e: Exception) {
                println(e)
                println("запрос не удался")
                if (_error.value != ErrorType.CONNECTION_ERROR) {
                    loadJokesFromCache()
                    _error.value = ErrorType.CONNECTION_ERROR
                }

            } finally {
                isLoadingMore = false
                _isLoading.value = false
            }
            _jokes.value = _jokes.value?.let { currentJokes ->
                currentJokes + loadedJokes
            } ?: loadedJokes
        }
        println("final")
        println(_jokes.value)
    }

    fun addJoke(category: String, question: String, answer: String) {
        val joke = UserJoke(category = category, question = question, answer = answer)
        viewModelScope.launch {
            repository.addUserJoke(joke)
            loadAllJokes()
        }
    }

    fun setCurrentJokeIndex (index: Int) {
        _currentJokeIndex.value = index
        updateCurrentJoke()
    }

    private fun updateCurrentJoke() {
        val jokesList = _jokesFlow.value
        val index = _currentJokeIndex.value ?: 0
        if (index in jokesList.indices) {
            _currentJoke.value = jokesList[index]
        } else {
            _currentJoke.value = Joke(-1, "error", "error", "error")
        }
    }

    private suspend fun loadJokesFromCache() {
        val cachedJokes = repository.getAllCachedJokes().firstOrNull() ?: emptyList()
        _jokesFlow.value += cachedJokes.map { cachedJoke ->
            Joke(
                id = cachedJoke.id,
                category = cachedJoke.category,
                question = cachedJoke.question,
                answer = cachedJoke.answer
            )
        }
    }
}