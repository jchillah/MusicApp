package de.syntax_institut.musicapp.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument


/**
 * Liefert die Routen-Konfiguration für alle Screens der App.
 */
object AppDestinations {
    // Routen ohne Parameter
    const val Home = "home"
    const val Search = "search"
    const val Profile = "profile"

    // Route mit Parameter {songId}
    const val Player = "player/{songId}"

    /**
     * Hilfsfunktion, um eine konkrete Player-Route mit Song-ID zu erzeugen.
     */
    fun PlayerRoute(songId: Int) = "player/$songId"

    /**
     * Argumentdefinition für den Player-Screen.
     */
    val playerArguments: List<NamedNavArgument>
        get() = listOf(
            navArgument("songId") { type = NavType.IntType }
        )

    /**
     * Liest aus dem NavBackStackEntry die Parameter und liefert das [PlayerRouteModel].
     */
    fun NavBackStackEntry.toPlayerRouteModel(): PlayerRouteModel {
        val id = arguments?.getInt("songId") ?: 0
        return PlayerRouteModel(id)
    }
}

/**
 * Datenklasse für die Parameter des Player-Screens.
 */
data class PlayerRouteModel(val songId: Int)
