package de.syntax_institut.musicapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.musicapp.ui.viewModel.SongListViewModel

/**
 * Zeigt eine vertikale Liste aller Songs.
 *
 * @param modifier            Optionaler [Modifier] für äußere Steuerung.
 * @param songListViewModel   ViewModel mit der Liste aller Songs.
 * @param onPlay              Callback, wenn ein Song gestartet werden soll.
 */
@Composable
fun SongsList(
    modifier: Modifier = Modifier,
    songListViewModel: SongListViewModel = viewModel(),
    onPlay: (Int) -> Unit,
) {
    val songs by songListViewModel.songs.collectAsState()

    LazyColumn(modifier = modifier) {
        items(songs, key = { it.id }) { song ->
            SongItem(
                song = song,
                onRemove = { id -> songListViewModel.removeSongById(id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPlay(song.id) },
            )
        }
    }
}

/** Vorschau der [SongsList]-Composable. */
@Preview(showBackground = true)
@Composable
fun SongsListPreview() {
    val vm = SongListViewModel()
    SongsList(songListViewModel = vm, onPlay = {})
}
