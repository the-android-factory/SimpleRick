package com.androidfactory.simplerick.repositories

import com.androidfactory.network.ApiOperation
import com.androidfactory.network.KtorClient
import com.androidfactory.network.models.domain.Character
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val ktorClient: KtorClient) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }
}