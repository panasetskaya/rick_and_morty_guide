package com.example.rickandmortyguide.domain

class GetCharacterByIdUseCase(private val repository: CharactersRepository) {

    fun getCharacterById(id: Int): Character {
        return repository.getCharacterById(id)
    }

}