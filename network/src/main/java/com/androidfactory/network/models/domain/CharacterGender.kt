package com.androidfactory.network.models.domain

sealed class CharacterGender() {
    object Male: CharacterGender()
    object Female: CharacterGender()
    object Genderless: CharacterGender()
    object Unknown: CharacterGender()
}