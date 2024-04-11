package com.androidfactory.simplerick.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.androidfactory.simplerick.ui.text.UiText
import com.androidfactory.simplerick.ui.theme.RickAction
import com.androidfactory.simplerick.ui.theme.RickTextPrimary

data class DataPoint(
    val title: String,
    val description: UiText
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
    val context = LocalContext.current
    Column {
        Text(
            text = dataPoint.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = RickAction
        )
        Text(
            text = dataPoint.description.asString(context),
            fontSize = 24.sp,
            color = RickTextPrimary
        )
    }
}

@Preview
@Composable
fun DataPointComponentPreview() {
    val data = DataPoint(
        title = "Last known location",
        description = UiText.DynamicText("Citadel of Ricks")
    )
    DataPointComponent(dataPoint = data)
}