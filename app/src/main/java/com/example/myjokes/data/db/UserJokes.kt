package com.example.myjokes.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user_jokes")
data class UserJoke(
    @SerialName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerialName("category")
    val category: String,
    @SerialName("setup")
    val question: String?,
    @SerialName("delivery")
    val answer: String?,
    val own: Boolean = true
)