package com.example.rickandmortyguide.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Character(
    @PrimaryKey
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "gender")
    val gender: String? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "species")
    val species: String? = null,
    @Json(name = "created")
    val created: String? = null,
    @Json(name = "image")
    val image: String? = null
)
