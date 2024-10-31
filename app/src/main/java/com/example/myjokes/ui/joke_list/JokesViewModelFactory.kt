package com.example.myjokes.ui.joke_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JokesViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokeListViewModel::class.java) -> {
                JokeListViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}