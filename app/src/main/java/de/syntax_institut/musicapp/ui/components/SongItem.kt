package de.syntax_institut.musicapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.syntax_institut.musicapp.data.Song
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme

/**
 * Composable zur Darstellung eines einzelnen Song-Eintrags.
 *
 * @param song     Song-Objekt mit allen notwendigen Informationen.
 * @param onRemove Callback, der die ID des Songs liefert, wenn er gelöscht werden soll.
 * @param modifier Optionaler [Modifier], um dieses Element von außen zu konfigurieren.
 */
@Composable
fun SongItem(
    song: Song,
    onRemove: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            // Hintergrundbild
            AsyncImage(
                model = song.coverUrl,
                contentDescription = "${song.title} Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            // Overlay mit Songinformationen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = song.duration,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    IconButton(onClick = { onRemove(song.id) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Löschen",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

/** Vorschau für SongItem ohne externe Callback-Verweise. */
@Preview(showBackground = true)
@Composable
fun SongItemPreview() {
    MusicAppTheme {
        SongItem(
            song = Song(
                id = 1,
                title = "Shape of You",
                artist = "Ed Sheeran",
                duration = "3:53",
                coverUrl = "https://i.ytimg.com/vi/bXSyEhwpJLo/maxresdefault.jpg"
            ),
            onRemove = {}
        )
    }
}

