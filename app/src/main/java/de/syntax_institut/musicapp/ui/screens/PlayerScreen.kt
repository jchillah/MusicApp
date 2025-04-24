package de.syntax_institut.musicapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import de.syntax_institut.musicapp.ui.navigation.AppDestinations
import de.syntax_institut.musicapp.ui.viewModel.PlayerViewModel
import de.syntax_institut.musicapp.ui.viewModel.SongListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    songId: Int,
    navController: NavController,
    songListViewModel: SongListViewModel = viewModel(),
    playerViewModel: PlayerViewModel = viewModel(),
    onMinimize: () -> Unit
) {
    val isPlaying by playerViewModel.isPlaying.collectAsState(initial = false)
    val position by playerViewModel.position.collectAsState(initial = 0f)
    val totalDuration by playerViewModel.totalDuration.collectAsState(initial = 0f)
    val songs  by songListViewModel.songs.collectAsState(initial = emptyList())
    val song  = songs.firstOrNull { it.id == songId } ?: return

    // Bei jedem Wechsel der Song-ID: Player neu initialisieren
    LaunchedEffect(songId) {
        playerViewModel.stopAndRelease()
        playerViewModel.updateTotalDuration(playerViewModel.parseDuration(song.duration))
        playerViewModel.playSong(song.audioUrl, playerViewModel.parseDuration(song.duration))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Player") },
                navigationIcon = {
                    IconButton(onClick = {
                        onMinimize()
                        navController.popBackStack(AppDestinations.Home, inclusive = false)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                },
                actions = {
                    IconButton(onClick = onMinimize) {
                        Icon(Icons.Default.ExpandMore, contentDescription = "Minimieren")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = song.coverUrl,
                contentDescription = "${song.title} Cover",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(16.dp))
            Text(song.title, style = MaterialTheme.typography.headlineSmall)
            Text(song.artist, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(24.dp))

            // Zeit-Anzeige
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(playerViewModel.formatDuration(position), style = MaterialTheme.typography.bodyMedium)
                Text(playerViewModel.formatDuration(totalDuration), style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(8.dp))

            // Slider
            Slider(
                value = position,
                onValueChange = { playerViewModel.updatePosition(it) },
                valueRange = 0f..totalDuration,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // Steuerung: vorher, play/pause, nächster
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val idx = songs.indexOf(song)
                    if (idx > 0) {
                        val prev = songs[idx - 1]
                        navController.navigate(AppDestinations.PlayerRoute(prev.id))
                    }
                }) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = "Vorheriger Song")
                }
                IconButton(onClick = { playerViewModel.togglePlayPause() }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }
                IconButton(onClick = {
                    val idx = songs.indexOf(song)
                    if (idx < songs.size - 1) {
                        val next = songs[idx + 1]
                        navController.navigate(AppDestinations.PlayerRoute(next.id))
                    }
                }) {
                    Icon(Icons.Default.SkipNext, contentDescription = "Nächster Song")
                }
            }
        }
    }
}
