package de.syntax_institut.musicapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.musicapp.ui.components.SongItem
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme
import de.syntax_institut.musicapp.ui.viewmodel.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onPlaySong: (Int) -> Unit,
    navController: NavController,
    viewModel: SongViewModel = viewModel()
) {
    val allSongs by viewModel.songs.collectAsState()
    var query by remember { mutableStateOf("") }
    val filtered = remember(query, allSongs) {
        allSongs.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.artist.contains(query, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suche") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Zurück"
                        )
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
                onValueChange = { query = it },
                label = { Text("Suche nach Song oder Künstler") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn {
                items(filtered, key = { it.id }) { song ->
                    SongItem(
                        song = song,
                        onRemove = { viewModel.removeSongById(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPlaySong(song.id) }
                            .padding(vertical = 4.dp, horizontal = 8.dp)
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
    val mockViewModel = SongViewModel().apply {
    }

    MusicAppTheme {
        SearchScreen(
            onPlaySong = {},
            navController = mockNavController,
            viewModel = mockViewModel
        )
    }
}
