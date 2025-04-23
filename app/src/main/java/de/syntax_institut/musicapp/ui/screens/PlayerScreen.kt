package de.syntax_institut.musicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.syntax_institut.musicapp.data.Song
import de.syntax_institut.musicapp.ui.viewmodel.SongViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    songId: Int,
    navController: NavController,
    viewModel: SongViewModel = viewModel()
) {
    val songs by viewModel.songs.collectAsState()
    val song: Song = songs.first { it.id == songId }

    var isPlaying by remember { mutableStateOf(false) }
    var position by remember { mutableFloatStateOf(0f) }

    val backgroundColor = MaterialTheme.colorScheme.background
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Player") },
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
                .padding(24.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = song.coverUrl,
                contentDescription = "${song.title} Cover",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(song.title, style = MaterialTheme.typography.headlineSmall, color = onBackgroundColor)
            Text(song.artist, style = MaterialTheme.typography.bodyMedium, color = onBackgroundColor)
            Spacer(modifier = Modifier.height(24.dp))

            Slider(
                value = position,
                onValueChange = { position = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* vorheriger Song */ }) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = "Zurück", tint = onBackgroundColor)
                }
                IconButton(onClick = { isPlaying = !isPlaying }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = onBackgroundColor
                    )
                }
                IconButton(onClick = { /* nächster Song */ }) {
                    Icon(Icons.Default.SkipNext, contentDescription = "Weiter", tint = onBackgroundColor)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerScreenPreview() {
    MusicAppTheme {
        val navController = rememberNavController()
        PlayerScreen(
            songId = 1,
            navController = navController,
            viewModel = SongViewModel()
        )
    }
}
