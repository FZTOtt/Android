package com.example.myjokes.data_clean.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// я так думаю здесь нет смысла сериализацию расписывать
@Serializable
@Entity(tableName = "cached_jokes")
data class CachedJokeDbModel (
    @SerialName("id")
    @PrimaryKey
    val id: Int,
    @SerialName("category")
    val category: String,
    @SerialName("setup")
    val question: String?,
    @SerialName("delivery")
    val answer: String?,
    val timestamp: Long
)