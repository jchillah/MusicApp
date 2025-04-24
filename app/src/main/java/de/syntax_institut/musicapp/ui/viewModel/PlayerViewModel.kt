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
    val position: StateFlow<Float> get() = _position

    private val _totalDuration = MutableStateFlow(0f)
    val totalDuration: StateFlow<Float> get() = _totalDuration

    private var positionJob: Job? = null

    /** Dauer im Format "m:ss" → Sekunden als Float */
    fun parseDuration(durationString: String): Float {
        val (m, s) = durationString.split(":").map { it.toInt() }
        return (m * 60 + s).toFloat()
    }

    private fun startUpdatingPosition() {
        positionJob?.cancel()
        positionJob = viewModelScope.launch {
            while (mediaPlayer?.isPlaying == true) {
                _position.value = (mediaPlayer?.currentPosition ?: 0).div(1000f)
                delay(1000)
            }
        }
    }

    private fun stopUpdatingPosition() {
        positionJob?.cancel()
        positionJob = null
    }

    /**
     * Setzt die Gesamtdauer (in Sekunden).
     * Wird bei jedem Songwechsel aus den Metadaten (parseDuration) gesetzt.
     */
    fun updateTotalDuration(durationInSeconds: Float) {
        _totalDuration.value = durationInSeconds
    }

    /**
     * Wird gerufen, wenn der Slider bewegt wird.
     */
    fun updatePosition(newPosition: Float) {
        _position.value = newPosition
        mediaPlayer?.seekTo((newPosition * 1000).toInt())
    }

    /**
     * Startet oder pausiert den Player.
     * Wenn ein neuer Song-URL übergeben wird, muss vorher stopAndRelease() aufgerufen werden.
     */
    fun playPause(songUrl: String) {
        if (mediaPlayer == null) {
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
        } else {
            if (_isPlaying.value) {
                mediaPlayer?.pause()
                stopUpdatingPosition()
            } else {
                mediaPlayer?.start()
                startUpdatingPosition()
            }
            _isPlaying.value = !_isPlaying.value
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

    /** Wird aufgerufen, wenn wir minimieren wollen (setzt Pause) */
    fun minimize() {
        mediaPlayer?.pause()
        _isPlaying.value = false
        stopUpdatingPosition()
    }
}
