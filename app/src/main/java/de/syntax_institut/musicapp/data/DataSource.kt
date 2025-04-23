package de.syntax_institut.musicapp.data

/**
 * Beispiel-Datenquelle für Songs.
 * Hier können später Songs aus einer Datenbank oder API geladen werden.
 */
object DataSource {
    /** Liste mit Beispiel-Songs. */
    val songs = listOf(
        Song(1, "Shape of You",    "Ed Sheeran",    "3:53", "https://i.ytimg.com/vi/bXSyEhwpJLo/maxresdefault.jpg"),
        Song(2, "Blinding Lights", "The Weeknd",    "3:20", "https://i.ytimg.com/vi/o3noebwFgro/maxresdefault.jpg"),
        Song(3, "Levitating",      "Dua Lipa",      "3:23", "https://i.ytimg.com/vi/l_sCrUpbt4I/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AHUBoAC4AOKAgwIABABGFsgXShlMA8=&rs=AOn4CLDBw7w_HkhJbAiwlkJSTpj1HL5pCg"),
        Song(4, "Peaches",         "Justin Bieber", "3:18", "https://trakfm.com/wp-content/uploads/2021/06/a236d5ab852d9e36351ca73f37ed9341.jpg"),
        Song(5, "Dance Monkey",    "Tones and I",   "3:29", "https://images.genius.com/ede1aee3ff875d14a591481f3cee0459.1000x1000x1.png")
    )
}
