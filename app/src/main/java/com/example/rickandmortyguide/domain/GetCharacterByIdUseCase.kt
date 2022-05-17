package com.example.rickandmortyguide.domain

class GetCharacterByIdUseCase(private val repository: CharactersRepository) {

    suspend fun getCharacterById(id: Int): Character {
        return repository.getCharacterById(id)
    }

}