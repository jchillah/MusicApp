package de.syntax_institut.musicapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import de.syntax_institut.musicapp.ui.theme.LightGold
import de.syntax_institut.musicapp.ui.theme.LightGreen
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme
import de.syntax_institut.musicapp.ui.viewModel.PlayerViewModel
import de.syntax_institut.musicapp.ui.viewModel.SongListViewModel

/**
 * Minimierte Player-Leiste, die den aktuell abgespielten Song kontrolliert.
 *
 * Zeigt Titel, Interpret, Cover und Steuerungsknöpfe für vorherigen, abspielen/pause,
 * nächsten Song sowie das Maximieren des Players an.
 *
 * @param onExpand          Callback, um den vollständigen Player-Screen zu öffnen.
 * @param songListViewModel ViewModel für Song-Liste und aktuelle Auswahl.
 * @param playerViewModel   ViewModel für MediaPlayer-Steuerung.
 * @param isDarkTheme       Gibt an, ob der Dark Mode aktiv ist.
 */
@Composable
fun BottomPlayerBar(
    onExpand: () -> Unit,
    songListViewModel: SongListViewModel = viewModel(),
    playerViewModel: PlayerViewModel = viewModel(),
    isDarkTheme: Boolean
) {
    val songs by songListViewModel.songs.collectAsState()
    val currentId by songListViewModel.currentSongId.collectAsState()
    val isPlaying by playerViewModel.isPlaying.collectAsState(initial = false)

    // Nur anzeigen, wenn ein Song ausgewählt
    val song = currentId?.let { id -> songs.firstOrNull { it.id == id } } ?: return
    val bg = if (isDarkTheme) LightGold else LightGreen

    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(bg)
            .clickable { onExpand() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = song.coverUrl,
            contentDescription = "${song.title} Cover",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(song.title, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
            Text(song.artist, style = MaterialTheme.typography.bodySmall, maxLines = 1)
        }
        // Vorheriger Song
        IconButton(onClick = {
            val idx = songs.indexOf(song)
            if (idx > 0) {
                val prev = songs[idx - 1]
                songListViewModel.selectSong(prev.id)
                playerViewModel.playPause(prev.audioUrl)
            }
        }) {
            Icon(
                Icons.Default.SkipPrevious,
                contentDescription = "Vorheriger",
                tint = Color.Black
            )
        }
        // Play/Pause
        IconButton(onClick = { playerViewModel.playPause(song.audioUrl) }) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color.Black
            )
        }
        // Nächster Song
        IconButton(onClick = {
            val idx = songs.indexOf(song)
            if (idx < songs.size - 1) {
                val next = songs[idx + 1]
                songListViewModel.selectSong(next.id)
                playerViewModel.playPause(next.audioUrl)
            }
        }) {
            Icon(
                Icons.Default.SkipNext,
                contentDescription = "Nächster",
                tint = Color.Black
            )
        }
        // Maximieren
        IconButton(onClick = onExpand) {
            Icon(
                Icons.Default.ExpandLess,
                contentDescription = "Maximieren",
                tint = Color.Black
            )
        }
    }
}

@Preview("BottomPlayerBar Light", showBackground = true)
@Preview(
    name = "BottomPlayerBar Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BottomPlayerBarPreviews() {
    val songsVm = SongListViewModel()
    val playerVm = PlayerViewModel()

    MusicAppTheme(darkTheme = false) {
        BottomPlayerBar(
            onExpand = {},
            songListViewModel = songsVm,
            playerViewModel = playerVm,
            isDarkTheme = false
        )
    }
    MusicAppTheme(darkTheme = true) {
        BottomPlayerBar(
            onExpand = {},
            songListViewModel = songsVm,
            playerViewModel = playerVm,
            isDarkTheme = true
        )
    }
}
