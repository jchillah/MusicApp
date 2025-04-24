@file:Suppress("KDocUnresolvedReference")

package de.syntax_institut.musicapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
 * Zeigt eine Karte mit Cover, Titel, Künstler und Löschen-Button für einen Song an.
 *
 * @param song            Das [Song]-Objekt mit Metadaten und Cover-URL.
 * @param onRemove        Callback, das die [song.id] liefert, wenn der Nutzer auf Löschen klickt.
 * @param modifier        Optionaler [Modifier], um das Layout von außen anzupassen.
 */
@Composable
fun SongItem(
    song: Song,
    onRemove: (Int) -> Unit,
    modifier: Modifier = Modifier,
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
            AsyncImage(
                model = song.coverUrl,
                contentDescription = "${song.title} Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
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

/** Vorschau der [SongItem]-Composable. */
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
                coverUrl = "https://...jpg",
                audioUrl = "https://...mp3"
            ),
            onRemove = {},
        )
    }
}