package com.androidfactory.simplerick.ui.text

import com.androidfactory.network.models.domain.CharacterGender
import com.androidfactory.simplerick.R

fun CharacterGender.displayNameResource(): Int {
    return when (this) {
        CharacterGender.Male -> R.string.character_gender_male
        CharacterGender.Female -> R.string.character_gender_female
        CharacterGender.Genderless -> R.string.character_gender_unspecified
        CharacterGender.Unknown -> R.string.character_gender_unknown
    }
}