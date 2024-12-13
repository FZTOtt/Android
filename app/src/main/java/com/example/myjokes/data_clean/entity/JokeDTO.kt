package com.example.myjokes.data_clean.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokeDTO (
    @SerialName("id")
    val id: Int,
    @SerialName("category")
    val category: String,
    @SerialName("setup")
    val question: String?,
    @SerialName("delivery")
    val answer: String?,
    val own: Boolean = false
)