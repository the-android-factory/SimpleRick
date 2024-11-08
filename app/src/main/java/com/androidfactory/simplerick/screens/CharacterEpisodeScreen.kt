package com.androidfactory.simplerick.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidfactory.network.KtorClient
import com.androidfactory.network.models.domain.Character
import com.androidfactory.network.models.domain.Episode
import com.androidfactory.simplerick.components.common.CharacterImage
import com.androidfactory.simplerick.components.common.CharacterNameComponent
import com.androidfactory.simplerick.components.common.DataPoint
import com.androidfactory.simplerick.components.common.DataPointComponent
import com.androidfactory.simplerick.components.common.LoadingState
import com.androidfactory.simplerick.components.common.SimpleToolbar
import com.androidfactory.simplerick.components.episode.EpisodeRowComponent
import com.androidfactory.simplerick.ui.theme.RickPrimary
import com.androidfactory.simplerick.ui.theme.RickTextPrimary
import kotlinx.coroutines.launch

sealed interface ScreenState {
    object Loading : ScreenState
    data class Success(val character: Character, val episodes: List<Episode>) : ScreenState
    data class Error(val message: String) : ScreenState
}

@Composable
fun CharacterEpisodeScreen(characterId: Int, ktorClient: KtorClient, onBackClicked: () -> Unit) {
    var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Loading) }

    LaunchedEffect(key1 = Unit, block = {
        ktorClient.getCharacter(characterId).onSuccess { character ->
            launch {
                ktorClient.getEpisodes(character.episodeIds).onSuccess { episodes ->
                    screenState = ScreenState.Success(character, episodes)
                }.onFailure {
                    screenState = ScreenState.Error(message = "Whoops, something went wrong")
                }
            }
        }.onFailure {
            screenState = ScreenState.Error(message = "Whoops, something went wrong")
        }
    })

    when (val state = screenState) {
        ScreenState.Loading -> LoadingState()
        is ScreenState.Error -> Text(
            text = state.message,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            textAlign = TextAlign.Center,
            fontSize = 26.sp
        )

        is ScreenState.Success -> MainScreen(
            character = state.character,
            episodes = state.episodes,
            onBackClicked = onBackClicked
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(character: Character, episodes: List<Episode>, onBackClicked: () -> Unit) {
    val episodeBySeasonMap = episodes.groupBy { it.seasonNumber }

    Column {
        SimpleToolbar(title = "Character episodes", onBackAction = onBackClicked)
        LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
            item { CharacterNameComponent(name = character.name) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                LazyRow {
                    episodeBySeasonMap.forEach { mapEntry ->
                        val title = "Season ${mapEntry.key}"
                        val description = "${mapEntry.value.size} ep"
                        item {
                            DataPointComponent(dataPoint = DataPoint(title, description))
                            Spacer(modifier = Modifier.width(32.dp))
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CharacterImage(imageUrl = character.imageUrl) }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            episodeBySeasonMap.forEach { mapEntry ->
                stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(mapEntry.value) { episode ->
                    EpisodeRowComponent(episode = episode)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = RickPrimary)
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Season $seasonNumber",
            color = RickTextPrimary,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = RickTextPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        )
    }
}