package de.syntax_institut.musicapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.musicapp.data.DataSource
import de.syntax_institut.musicapp.data.Song
import kotlinx.coroutines.flow.*

/**
 * ViewModel für die Verwaltung der Song-Daten und Suchfunktion.
 */
class SongViewModel : ViewModel() {
    // Intern: Liste aller Songs
    private val _songs = MutableStateFlow(DataSource.songs)

    /** Flow mit der Liste aller Songs (nur lesbar von außen). */
    val songs: StateFlow<List<Song>> = _songs.asStateFlow()

    // Intern: aktueller Suchbegriff
    private val _query = MutableStateFlow("")

    /** Flow mit dem aktuellen Suchbegriff. */
    val query: StateFlow<String> = _query.asStateFlow()

    /**
     * Aktualisiert den Suchbegriff und löst Neuberechnung der gefilterten Liste aus.
     */
    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    /**
     * Gefilterte Song-Liste basierend auf Suchbegriff.
     */
    val filteredSongs: StateFlow<List<Song>> = combine(_songs, _query) { list, q ->
        if (q.isBlank()) list
        else list.filter {
            it.title.contains(q, ignoreCase = true) || it.artist.contains(
                q,
                ignoreCase = true
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _songs.value
    )

    /** Entfernt einen Song anhand seiner ID aus der Liste. */
    fun removeSongById(songId: Int) {
        _songs.value = _songs.value.filterNot { it.id == songId }
    }

    fun loadSongs(newList: List<Song>) {
        _songs.value = newList
    }

    // Wiedergabezustand
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    // Position des Songs (z. B. Fortschritt in Sekunden)
    private val _position = MutableStateFlow(0f)
    val position: StateFlow<Float> = _position.asStateFlow()

    fun updatePosition(newPosition: Float) {
        _position.value = newPosition
    }
}