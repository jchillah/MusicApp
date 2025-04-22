package de.syntax_institut.musicapp.data

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val duration: String,
    val coverUrl: String
)
