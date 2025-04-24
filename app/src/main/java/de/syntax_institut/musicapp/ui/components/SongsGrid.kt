package de.syntax_institut.musicapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.musicapp.ui.viewModel.SongListViewModel

/**
 * Zeigt eine Gitteransicht (2 Spalten) aller Songs.
 *
 * @param modifier            Optionaler [Modifier] für äußere Steuerung.
 * @param songListViewModel   ViewModel mit der Liste aller Songs.
 * @param onPlay              Callback, wenn ein Song gestartet werden soll.
 */
@Composable
fun SongsGrid(
    modifier: Modifier = Modifier,
    songListViewModel: SongListViewModel = viewModel(),
    onPlay: (Int) -> Unit,
) {
    val songs by songListViewModel.songs.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(songs, key = { it.id }) { song ->
            SongItem(
                song = song,
                onRemove = { id -> songListViewModel.removeSongById(id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { onPlay(song.id) },
            )
        }
    }
}

/** Vorschau der [SongsGrid]-Composable. */
@Preview(showBackground = true)
@Composable
fun SongsGridPreview() {
    val vm = SongListViewModel()
    SongsGrid(
        songListViewModel = vm,
        onPlay = {}
    )
}