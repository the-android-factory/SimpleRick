package com.androidfactory.simplerick.ui.text

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiText {
    data class StringResource(@StringRes val resId: Int) : UiText
    data class DynamicText(val value: String) : UiText

    fun asString(context: Context): String {
        return when (this) {
            is StringResource -> context.getString(resId)
            is DynamicText -> value
        }
    }
}