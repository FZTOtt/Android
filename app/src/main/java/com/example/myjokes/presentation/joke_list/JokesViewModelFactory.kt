package com.example.myjokes.presentation.joke_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myjokes.domain.repository.JokeRepository
import com.example.myjokes.domain.usecase.AddUserJokeUseCase
import com.example.myjokes.domain.usecase.ClearOldCachedJokesUseCase
import com.example.myjokes.domain.usecase.GetAllCachedJokesUseCase
import com.example.myjokes.domain.usecase.GetAllUserJokesUseCase
import com.example.myjokes.domain.usecase.GetJokesFromNetUseCase
import com.example.myjokes.domain.usecase.SaveCachedJokesUseCase

class JokesViewModelFactory(private val repository: JokeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val addUserJokeUseCase = AddUserJokeUseCase(repository)
        val clearOldCachedJokesUseCase = ClearOldCachedJokesUseCase(repository)
        val getAllCachedJokesUseCase = GetAllCachedJokesUseCase(repository)
        val getAllUserJokesUseCase = GetAllUserJokesUseCase(repository)
        val getJokesFromNetUseCase = GetJokesFromNetUseCase(repository)
        val saveCachedJokesUseCase = SaveCachedJokesUseCase(repository)

        return when {
            modelClass.isAssignableFrom(JokeListViewModel::class.java) -> {
                JokeListViewModel(
                    addUserJokeUseCase,
                    clearOldCachedJokesUseCase,
                    getAllCachedJokesUseCase,
                    getAllUserJokesUseCase,
                    getJokesFromNetUseCase,
                    saveCachedJokesUseCase
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}