package de.syntax_institut.musicapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
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
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Coverbild
            AsyncImage(
                model = song.coverUrl,
                contentDescription = "${song.title} Cover",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Titel & Künstler
            Column(modifier = Modifier.weight(1f)) {
                Text(song.title, style = MaterialTheme.typography.titleMedium)
                Text(song.artist, style = MaterialTheme.typography.bodyMedium)
            }

            // Dauer & Löschen-Button
            Column(horizontalAlignment = Alignment.End) {
                Text(song.duration, style = MaterialTheme.typography.bodySmall)
                IconButton(onClick = { onRemove(song.id) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Löschen")
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

