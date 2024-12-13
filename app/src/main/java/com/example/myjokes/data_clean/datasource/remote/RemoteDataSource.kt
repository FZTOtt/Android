package com.example.myjokes.data_clean.datasource.remote

import JokesApiResponse

interface RemoteDataSource {

    suspend fun getRandomJokes(): JokesApiResponse
}