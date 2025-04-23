package de.syntax_institut.musicapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.syntax_institut.musicapp.ui.viewmodel.SongViewModel

/**
 * Gitter-Ansicht mit Klick-Callback.
 *
 * @param viewModel Liefert die Song-Daten.
 * @param onPlay Callback, wenn ein Song angeklickt wird.
 * @param modifier Optionaler Modifier.
 */
@Composable
fun SongsGrid(
    viewModel: SongViewModel,
    onPlay: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val songs by viewModel.songs.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(songs, key = { it.id }) { song ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable { onPlay(song.id) }
            ) {
                SongItem(
                    song = song,
                    onRemove = { id -> viewModel.removeSongById(id) },
                    modifier = Modifier
                        .fillMaxWidth()    // oder .fillMaxSize(), je nach gewünschtem Layout
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongsGridPreview() {
    val vm = SongViewModel()
    SongsGrid(
        viewModel = vm,
        onPlay = {}
    )
}
