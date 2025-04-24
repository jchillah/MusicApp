package de.syntax_institut.musicapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn                         // Für LazyColumn :contentReference[oaicite:0]{index=0}
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.syntax_institut.musicapp.ui.viewModel.SongViewModel

/**
 * Vertikale Song-Liste mit Klick-Callback.
 *
 * @param viewModel Liefert die Song-Daten.
 * @param onPlay    Callback, wenn ein Song angeklickt wird.
 * @param modifier  Optionaler Modifier.
 */
@Composable
fun SongsList(
    viewModel: SongViewModel,
    onPlay: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val songs by viewModel.songs.collectAsState()

    LazyColumn(modifier = modifier) {
        items(songs, key = { it.id }) { song ->
            SongItem(
                song = song,
                onRemove = { id -> viewModel.removeSongById(id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPlay(song.id) }
            )
        }
    }
}

/** Vorschau für SongsList. */
@Preview(showBackground = true)
@Composable
fun SongsListPreview() {
    val vm = SongViewModel()
    SongsList(viewModel = vm, onPlay = {})
}
