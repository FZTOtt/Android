package com.example.myjokes.domain.entity

data class Joke (
    val id: Int,
    val category: String,
    val question: String?,
    val answer: String?,
    val own: Boolean = false
)