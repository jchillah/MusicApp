package de.syntax_institut.musicapp.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.syntax_institut.musicapp.data.PlayerRouteModel

/**
 * Zentralisierte Routen-Konfiguration für die Navigation in der MusicApp.
 *
 * Hier werden alle Destinations (Bildschirme) und deren Parameter definiert.
 */
object AppDestinations {
    /** Route für den Home-Screen. */
    const val Home = "home"

    /** Route für den Search-Screen. */
    const val Search = "search"

    /** Route für den Profile-Screen. */
    const val Profile = "profile"

    /**
     * Route für den Player-Screen mit Platzhalter für die Song-ID.
     * Die ID wird beim Navigieren in die tatsächliche Route eingesetzt.
     */
    const val Player = "player/{songId}"

    /**
     * Hilfsfunktion zum Erzeugen einer konkreten Player-Route mit übergebener Song-ID.
     *
     * @param songId Die eindeutige Kennung des abzuspielenden Songs.
     * @return Vollständige Route im Format "player/42".
     */
    fun PlayerRoute(songId: Int): String = "player/$songId"

    /**
     * Definition der Argumente, die der Player-Screen erwartet.
     * Hier wird angegeben, dass "songId" als Integer übergeben wird.
     */
    val playerArguments: List<NamedNavArgument>
        get() = listOf(
            navArgument("songId") { type = NavType.IntType }
        )

    /**
     * Erweiterungsfunktion, um aus einem NavBackStackEntry das PlayerRouteModel zu extrahieren.
     *
     * @receiver NavBackStackEntry mit den geladenen Argumenten.
     * @return PlayerRouteModel mit der ausgelesenen Song-ID.
     */
    fun NavBackStackEntry.toPlayerRouteModel(): PlayerRouteModel {
        val id = arguments?.getInt("songId") ?: 0
        return PlayerRouteModel(id)
    }
}