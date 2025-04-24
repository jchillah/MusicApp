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
            "3:54",
            "https://i.ytimg.com/vi/bXSyEhwpJLo/maxresdefault.jpg",
            audioUrl = "https://archive.org/download/shape-of-you-ed-sheeran/Shape%20Of%20You%20-%20Ed%20Sheeran.mp3"
        ),
        Song(
            2,
            "Blinding Lights",
            "The Weeknd",
            "3:23",
            "https://i.ytimg.com/vi/o3noebwFgro/maxresdefault.jpg",
            audioUrl = "https://archive.org/download/the-weeknd-blinding-lights-official-audio_202103/The%20Weeknd%20-%20Blinding%20Lights%20%28Official%20Audio%29.mp3"
        ),
        Song(
            3,
            "Levitating",
            "Dua Lipa",
            "3:23",
            "https://i.ytimg.com/vi/l_sCrUpbt4I/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AHUBoAC4AOKAgwIABABGFsgXShlMA8=&rs=AOn4CLDBw7w_HkhJbAiwlkJSTpj1HL5pCg",
            audioUrl = "https://archive.org/download/dua-lipa-levitating-feat-da-baby-/Dua%20Lipa%20-%20Levitating%20feat.%20DaBaby.mp3"
        ),
        Song(
            4,
            "Peaches",
            "Justin Bieber",
            "3:18",
            "https://trakfm.com/wp-content/uploads/2021/06/a236d5ab852d9e36351ca73f37ed9341.jpg",
            audioUrl = "https://archive.org/download/justin-bieber-peaches-ft.-daniel-caesar-giveon/Justin%20Bieber%20-%20Peaches%20ft.%20Daniel%20Caesar%2C%20Giveon.mp3"
        ),
        Song(
            5,
            "Dance Monkey",
            "Tones and I",
            "3:29",
            "https://images.genius.com/ede1aee3ff875d14a591481f3cee0459.1000x1000x1.png",
            audioUrl = "https://archive.org/download/Tones-And-I-Dance-Monkey/Tones%20And%20I%20-%20Dance%20Monkey.mp3"
        )
    )
}