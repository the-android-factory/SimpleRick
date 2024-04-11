package com.androidfactory.simplerick.components.character

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidfactory.network.models.domain.CharacterStatus
import com.androidfactory.simplerick.ui.text.displayNameResource
import com.androidfactory.simplerick.ui.theme.RickTextPrimary
import com.androidfactory.simplerick.ui.theme.SimpleRickTheme

@Composable
fun CharacterStatusComponent(characterStatus: CharacterStatus) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = characterStatus.color,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Status: ${stringResource(characterStatus.displayNameResource())}",
            fontSize = 20.sp,
            color = RickTextPrimary
        )
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewAlive() {
    SimpleRickTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Alive)
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewDead() {
    SimpleRickTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Dead)
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewUnknown() {
    SimpleRickTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Unknown)
    }
}