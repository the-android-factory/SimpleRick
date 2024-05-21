package com.androidfactory.network.models.domain

import androidx.compose.ui.graphics.Color

sealed class CharacterStatus(val color: Color) {
    object Alive: CharacterStatus(Color.Green)
    object Dead: CharacterStatus(Color.Red)
    object Unknown: CharacterStatus(Color.Yellow)
}