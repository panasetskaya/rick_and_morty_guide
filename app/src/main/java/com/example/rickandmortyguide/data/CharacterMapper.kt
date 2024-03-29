package com.example.rickandmortyguide.data

import com.example.rickandmortyguide.domain.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor() {

    fun mapDbModeltoDomainEntity(dbModel: CharacterDtoDb): Character {
        val created = dbModel.created?.substring(0, 10)
        return Character(
            dbModel.id,
            dbModel.name,
            dbModel.gender,
            dbModel.status,
            dbModel.species,
            created,
            dbModel.image
        )
    }
}