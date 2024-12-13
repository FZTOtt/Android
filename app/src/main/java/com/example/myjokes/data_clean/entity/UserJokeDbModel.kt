package com.example.myjokes.data_clean.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// и тут тоже сериализацию не за чем, так как для работы с сетью есть JokeDTO
@Serializable
@Entity(tableName = "user_jokes")
data class UserJokeDbModel (
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