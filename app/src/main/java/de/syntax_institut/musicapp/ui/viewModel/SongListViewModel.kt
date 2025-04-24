package de.syntax_institut.musicapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.musicapp.data.DataSource
import de.syntax_institut.musicapp.data.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SongListViewModel : ViewModel() {
    private val _currentSongId = MutableStateFlow<Int?>(null)
    val currentSongId: StateFlow<Int?> = _currentSongId.asStateFlow()

    private val _songs = MutableStateFlow(DataSource.songs)
    val songs: StateFlow<List<Song>> = _songs.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val filteredSongs: StateFlow<List<Song>> = combine(_songs, _query) { list, q ->
        if (q.isBlank()) list
        else list.filter {
            it.title.contains(q, ignoreCase = true) || it.artist.contains(q, ignoreCase = true)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _songs.value
    )

    fun selectSong(id: Int) {
        _currentSongId.value = id
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun removeSongById(songId: Int) {
        _songs.value = _songs.value.filterNot { it.id == songId }
    }

    fun loadSongs(newList: List<Song>) {
        _songs.value = newList
    }
}
