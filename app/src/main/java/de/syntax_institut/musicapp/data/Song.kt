package de.syntax_institut.musicapp.data

/**
 * Datenmodell für einen Song in der MusicApp.
 *
 * @property id           Eindeutige Kennung des Songs.
 * @property title        Titel des Songs.
 * @property artist       Interpret oder Band.
 * @property duration     Spielzeit des Songs im Format "m:ss".
 * @property coverUrl     URL zum Coverbild des Songs.
 * @property audioUrl     URL zur Audiodatei des Songs.
 */
data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val duration: String,
    val coverUrl: String,
    val audioUrl: String
)