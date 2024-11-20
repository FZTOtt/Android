package com.example.myjokes.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Joke (
    @SerialName("id")
    val id: Int,
    @SerialName("category")
    val category: String,
    @SerialName("setup")
    val question: String?,
    @SerialName("delivery")
    val answer: String?
)