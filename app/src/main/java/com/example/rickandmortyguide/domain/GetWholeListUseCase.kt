package com.example.rickandmortyguide.domain

class GetWholeListUseCase(private val repository: CharactersRepository) {

    fun getWholeList(): List<Character> {
        return repository.getWholeList()
    }

}