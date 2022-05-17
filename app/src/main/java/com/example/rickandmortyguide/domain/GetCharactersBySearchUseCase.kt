package com.example.rickandmortyguide.domain

class GetCharactersBySearchUseCase(private val repository: CharactersRepository) {

    suspend fun getCharactersBySearch(query: String): List<Character> {
        return repository.getCharactersBySearch(query)
    }

}