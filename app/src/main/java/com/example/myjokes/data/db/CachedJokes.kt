package com.example.myjokes.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cached_jokes")
data class CachedJoke(
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