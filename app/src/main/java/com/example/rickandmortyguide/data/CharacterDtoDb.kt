package com.example.rickandmortyguide.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class CharacterDtoDb(
    @PrimaryKey
    @Json(name = "id")
    val id: Int,
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
