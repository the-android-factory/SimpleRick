package com.androidfactory.simplerick.ui.text

import com.androidfactory.network.models.domain.CharacterStatus
import com.androidfactory.simplerick.R

fun CharacterStatus.displayNameResource(): Int {
    return when (this) {
        CharacterStatus.Alive -> R.string.character_status_alive
        CharacterStatus.Dead -> R.string.character_status_dead
        CharacterStatus.Unknown -> R.string.character_status_unknown
    }
}