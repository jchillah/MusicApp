package de.syntax_institut.musicapp.ui.viewModel

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class PlayerViewModel : ViewModel() {

    private var mediaPlayer: MediaPlayer? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _position = MutableStateFlow(0f)
    val position: StateFlow<Float> = _position

    private val _totalDuration = MutableStateFlow(0f)
    val totalDuration: StateFlow<Float> = _totalDuration

    private var positionJob: Job? = null

    /** Duration im Format "m:ss" → Sekunden als Float */
    fun parseDuration(durationString: String): Float {
        val (m, s) = durationString.split(":").map { it.toInt() }
        return (m * 60 + s).toFloat()
    }

    private fun startUpdatingPosition() {
        positionJob?.cancel()
        positionJob = viewModelScope.launch {
            while (mediaPlayer?.isPlaying == true) {
                _position.value = (mediaPlayer?.currentPosition ?: 0) / 1000f
                delay(1000)
            }
        }
    }

    private fun stopUpdatingPosition() {
        positionJob?.cancel()
        positionJob = null
    }

    /** Setzt die Gesamtdauer (in Sekunden). */
    fun updateTotalDuration(seconds: Float) {
        _totalDuration.value = seconds
    }

    /** Springt im laufenden Song an eine neue Position. */
    fun updatePosition(newPos: Float) {
        _position.value = newPos
        mediaPlayer?.seekTo((newPos * 1000).toInt())
    }

    /**
     * Startet einen komplett neuen Track (beendet vorherigen, lädt und startet neu).
     */
    fun playSong(songUrl: String, durationSeconds: Float) {
        // alten Player freigeben
        stopAndRelease()
        // neue Dauer setzen
        _totalDuration.value = durationSeconds
        // neuen Player aufbauen
        mediaPlayer = MediaPlayer().apply {
            setDataSource(songUrl)
            prepareAsync()
            setOnPreparedListener {
                start()
                _isPlaying.value = true
                startUpdatingPosition()
            }
            setOnCompletionListener {
                stopAndRelease()
            }
        }
    }

    /** Toggles Pause/Continue im aktuellen Track */
    fun togglePlayPause() {
        mediaPlayer?.let { mp ->
            if (mp.isPlaying) {
                mp.pause()
                _isPlaying.value = false
                stopUpdatingPosition()
            } else {
                mp.start()
                _isPlaying.value = true
                startUpdatingPosition()
            }
        }
    }

    /** Stoppt und gibt den MediaPlayer frei */
    fun stopAndRelease() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlaying.value = false
        _position.value = 0f
        stopUpdatingPosition()
    }

    override fun onCleared() {
        super.onCleared()
        stopAndRelease()
    }

    /** Formatierung für Anzeige (mm:ss) */
    fun formatDuration(seconds: Float): String {
        val total = seconds.toInt()
        val m = total / 60
        val s = total % 60
        return String.format(Locale.getDefault(), "%02d:%02d", m, s)
    }

    /** Wird beim Minimieren aufgerufen (pausiert) */
    fun minimize() {
        mediaPlayer?.pause()
        _isPlaying.value = false
        stopUpdatingPosition()
    }
}
