package com.example.myjokes.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokesApiResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("amount")
    val amount: Int,
    @SerialName("jokes")
    val jokes: List<Joke>
)