package com.example.rickandmortyguide.data

import com.example.rickandmortyguide.domain.Character
import com.squareup.moshi.Json

data class Example(
    @Json(name = "results")
    val characters: List<Character>? = null
)
