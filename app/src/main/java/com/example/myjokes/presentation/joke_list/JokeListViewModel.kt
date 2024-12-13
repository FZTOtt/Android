package com.example.myjokes.presentation.joke_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjokes.domain.entity.ErrorType
import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.data_clean.datasource.remote.RetrofitInstance
import com.example.myjokes.data_clean.datasource.local.AppDatabase
import com.example.myjokes.domain.usecase.AddUserJokeUseCase
import com.example.myjokes.domain.usecase.ClearOldCachedJokesUseCase
import com.example.myjokes.domain.usecase.GetAllCachedJokesUseCase
import com.example.myjokes.domain.usecase.GetAllUserJokesUseCase
import com.example.myjokes.domain.usecase.GetJokesFromNetUseCase
import com.example.myjokes.domain.usecase.SaveCachedJokesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class JokeListViewModel(
    private val addUserJokeUseCase: AddUserJokeUseCase,
    private val clearOldCachedJokesUseCase: ClearOldCachedJokesUseCase,
    private val getAllCachedJokesUseCase: GetAllCachedJokesUseCase,
    private val getAllUserJokesUseCase: GetAllUserJokesUseCase,
    private val getJokesFromNetUseCase: GetJokesFromNetUseCase,
    private val saveCachedJokesUseCase: SaveCachedJokesUseCase
): ViewModel() {

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

    fun loadAllJokes() {
        println("load all jokes")

        refreshJokes()
        println(_jokes.value)
        getJokes()
        println(_jokes.value)
        viewModelScope.launch {
            _isLoading.value = true
            combine(getAllUserJokesUseCase(), getAllCachedJokesUseCase()) {
                userJokes, cachedJokes ->

                val jokes = mutableListOf<Joke>()
                jokes.addAll(userJokes)

                if (_error.value == ErrorType.CONNECTION_ERROR) {
                    jokes.addAll(cachedJokes)
                } else {
                    println("из сети")
                    _jokes.value?.let { jokes.addAll(it) }
                }
                jokes
            }.collect { combinedJokes ->
                _jokesFlow.value = combinedJokes
                println("после добавления шуток")
                println(_jokesFlow.value)
                _isLoading.value = false
            }
        }
        println("after load all")
        println(_jokesFlow.value)
    }

    private fun refreshJokes() {
        viewModelScope.launch {
            clearOldCachedJokesUseCase()
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
                val jokes = getJokesFromNetUseCase()
                loadedJokes.addAll(jokes)
                saveCachedJokesUseCase(loadedJokes)
                if (_error.value == ErrorType.CONNECTION_ERROR) {
                    _error.value = ErrorType.NONE
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
        val joke = Joke(id=0, category = category, question = question, answer = answer)
        viewModelScope.launch {
            addUserJokeUseCase(joke)
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
        val cachedJokes = getAllCachedJokesUseCase().firstOrNull() ?: emptyList()
        _jokesFlow.value += cachedJokes
    }
}