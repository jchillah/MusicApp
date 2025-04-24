package de.syntax_institut.musicapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object SearchRoute

@Serializable
object ProfileRoute

@Serializable
data class PlayerRoute(val songId: Int)
