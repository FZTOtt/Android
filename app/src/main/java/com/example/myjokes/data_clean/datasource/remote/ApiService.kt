package com.example.myjokes.data_clean.datasource.remote

import JokesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("joke/Any")
    suspend fun getRandomJokes(
        @Query("blacklistFlags") blacklistFlags: String = "nsfw,religious,political,racist,sexist,explicit",
        @Query("type") type: String = "twopart",
        @Query("amount") amount: Int = 10
    ): JokesApiResponse
}