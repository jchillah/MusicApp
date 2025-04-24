package de.syntax_institut.musicapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.musicapp.ui.components.SongItem
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme
import de.syntax_institut.musicapp.ui.viewModel.PlayerViewModel
import de.syntax_institut.musicapp.ui.viewModel.SongListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onPlaySong: (Int) -> Unit,
    navController: NavController,
    songListViewModel: SongListViewModel = viewModel(),
) {
    val query by songListViewModel.query.collectAsState()
    val filtered by songListViewModel.filteredSongs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suche") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { songListViewModel.updateQuery(it) },
                label = { Text("Suche nach Song oder Künstler") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Suchbegriff eingeben...") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(filtered, key = { it.id }) { song ->
                    SongItem(
                        song = song,
                        onRemove = { songListViewModel.removeSongById(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPlaySong(song.id) }
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                    )
                }
            }
        }
    }
}

/** Vorschau für SearchScreen. */
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val mockNavController = rememberNavController()
    val mockViewModel = SongListViewModel().apply {}
    PlayerViewModel().apply {}

    MusicAppTheme {
        SearchScreen(
            onPlaySong = {},
            navController = mockNavController,
            songListViewModel = mockViewModel,
        )
    }
}
