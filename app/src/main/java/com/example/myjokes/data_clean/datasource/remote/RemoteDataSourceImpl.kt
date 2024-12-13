package com.example.myjokes.data_clean.datasource.remote

import JokesApiResponse

class RemoteDataSourceImpl(
    private val api: ApiService
): RemoteDataSource {
    override suspend fun getRandomJokes(): JokesApiResponse {
        return api.getRandomJokes()
    }
}