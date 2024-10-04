package com.androidfactory.simplerick.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.androidfactory.simplerick.components.common.SimpleToolbar
import com.androidfactory.simplerick.ui.theme.RickAction
import com.androidfactory.simplerick.ui.theme.RickPrimary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SimpleToolbar(title = "Search")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search icon",
                    tint = RickPrimary
                )
                BasicTextField(
                    state = searchViewModel.searchTextFieldState,
                    modifier = Modifier.weight(1f)
                )
            }
            AnimatedVisibility(visible = searchViewModel.searchTextFieldState.text.isNotBlank()) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete icon",
                    tint = RickAction,
                    modifier = Modifier.clickable {
                        searchViewModel.searchTextFieldState.edit { delete(0, length) }
                    }
                )
            }
        }

        val searchText by searchViewModel.searchTextState.collectAsStateWithLifecycle()
        Text(
            text = searchText,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            textAlign = TextAlign.Center,
            fontSize = 26.sp
        )
    }
}

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    val searchTextFieldState = TextFieldState()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchTextState = snapshotFlow { searchTextFieldState.text }
        .debounce(500)
        .mapLatest { if (it.isBlank()) "Awaiting your command ..." else it.toString() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 2000),
            initialValue = ""
        )
}