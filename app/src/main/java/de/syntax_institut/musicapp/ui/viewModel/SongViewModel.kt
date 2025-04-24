package de.syntax_institut.musicapp.ui.viewModel

import androidx.lifecycle.ViewModel
import de.syntax_institut.musicapp.data.DataSource
import de.syntax_institut.musicapp.data.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel für die Verwaltung der Song-Daten.
 */
class SongViewModel : ViewModel() {
    // Mutabler StateFlow, intern schreibbar
    private val _songs = MutableStateFlow(DataSource.songs)

    /** StateFlow mit der Liste aller Songs (nur lesbar von außen). */
    val songs: StateFlow<List<Song>> = _songs.asStateFlow()

    /**
     * Entfernt einen Song anhand seiner ID aus der Liste.
     *
     * @param songId ID des zu löschenden Songs.
     */
    fun removeSongById(songId: Int) {
        _songs.value = _songs.value.filterNot { it.id == songId }
    }
}