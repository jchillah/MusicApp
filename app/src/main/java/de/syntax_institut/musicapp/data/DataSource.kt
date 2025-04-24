package de.syntax_institut.musicapp.data

// package de.syntax_institut.musicapp.data

/**
 * DataSource liefert eine Liste von Beispiel-Songs.
 *
 * Diese Quelle kann später durch eine API oder Datenbank ersetzt werden.
 */
object DataSource {
    /**
     * Beispiel-Liste von Songs mit Metadaten und Audio-URL.
     */

    val songs = listOf(
        Song(
            1,
            "Shape of You",
            "Ed Sheeran",
            "3:53",
            "https://i.ytimg.com/vi/bXSyEhwpJLo/maxresdefault.jpg",
            audioUrl = "https://archive.org/download/shape-of-you-ed-sheeran/Shape%20Of%20You%20-%20Ed%20Sheeran.mp3"
        ),
        Song(
            2,
            "Blinding Lights",
            "The Weeknd",
            "3:20",
            "https://i.ytimg.com/vi/o3noebwFgro/maxresdefault.jpg",
            audioUrl = "https://cdn.pixabay.com/download/audio/2024/10/21/audio_78251ef8e3.mp3?filename=beautiful-loop-253269.mp3"
        ),
        Song(
            3,
            "Levitating",
            "Dua Lipa",
            "3:23",
            "https://i.ytimg.com/vi/l_sCrUpbt4I/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AHUBoAC4AOKAgwIABABGFsgXShlMA8=&rs=AOn4CLDBw7w_HkhJbAiwlkJSTpj1HL5pCg",
            audioUrl = "https://cdn.pixabay.com/download/audio/2024/02/28/audio_60f7a54400.mp3?filename=rick-ross-x-nas-type-sample-beat-2024-193647.mp3"
        ),
        Song(
            4,
            "Peaches",
            "Justin Bieber",
            "3:18",
            "https://trakfm.com/wp-content/uploads/2021/06/a236d5ab852d9e36351ca73f37ed9341.jpg",
            audioUrl = "https://cdn.pixabay.com/download/audio/2024/02/28/audio_d1a9995fc3.mp3?filename=sample-type-rap-beat-lucky-night-193659.mp3"
        ),
        Song(
            5,
            "Dance Monkey",
            "Tones and I",
            "3:29",
            "https://images.genius.com/ede1aee3ff875d14a591481f3cee0459.1000x1000x1.png",
            audioUrl = "https://cdn.pixabay.com/download/audio/2024/04/27/audio_5e68a7fb68.mp3?filename=whiplash-audio-tonic-204874.mp3"
        )
    )
}