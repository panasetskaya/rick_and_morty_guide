package com.example.rickandmortyguide.data.network

import com.example.rickandmortyguide.data.CharacterDtoDb
import com.squareup.moshi.Json

data class CharactersListContainer(
    @Json(name = "results")
    val characters: List<CharacterDtoDb>
)
