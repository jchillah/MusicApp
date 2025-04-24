package de.syntax_institut.musicapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.syntax_institut.musicapp.ui.components.SongsGrid
import de.syntax_institut.musicapp.ui.components.SongsList
import de.syntax_institut.musicapp.ui.theme.DeepBlack
import de.syntax_institut.musicapp.ui.theme.LightGold
import de.syntax_institut.musicapp.ui.theme.LightGreen
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme
import de.syntax_institut.musicapp.ui.viewModel.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onPlaySong: (Int) -> Unit,
    viewModel: SongViewModel,
    isDarkTheme: Boolean
) {
    var isGrid by remember { mutableStateOf(false) }
    val fabBackgroundColor = if (isDarkTheme) LightGold else LightGreen
    val gridButtonColor = if (isDarkTheme) LightGold else LightGreen
    val searchButtonColor = if (isDarkTheme) LightGold else LightGreen
    val iconColor = if (isDarkTheme) DeepBlack else DeepBlack

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meine Musik", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { isGrid = !isGrid }) {
                        Icon(
                            imageVector = if (isGrid) Icons.AutoMirrored.Filled.List else Icons.Default.GridView,
                            contentDescription = if (isGrid) "Listenansicht" else "Gitteransicht",
                            tint = gridButtonColor
                        )
                    }
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Suche",
                            tint = searchButtonColor
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToProfile,
                containerColor = fabBackgroundColor
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profil",
                    tint = iconColor
                )
            }
        }
    ) { padding ->
        if (isGrid) {
            SongsGrid(
                viewModel = viewModel,
                onPlay = onPlaySong,
                modifier = Modifier.padding(padding)
            )
        } else {
            SongsList(
                viewModel = viewModel,
                onPlay = onPlaySong,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MusicAppTheme {
        HomeScreen(
            onPlaySong = {},
            onNavigateToProfile = {},
            viewModel = SongViewModel(),
            isDarkTheme = false,
            onNavigateToSearch = {}
        )
    }
}
