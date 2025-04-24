package de.syntax_institut.musicapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import de.syntax_institut.musicapp.ui.components.BottomPlayerBar
import de.syntax_institut.musicapp.ui.navigation.AppDestinations
import de.syntax_institut.musicapp.ui.navigation.AppDestinations.toPlayerRouteModel
import de.syntax_institut.musicapp.ui.screens.HomeScreen
import de.syntax_institut.musicapp.ui.screens.PlayerScreen
import de.syntax_institut.musicapp.ui.screens.ProfileScreen
import de.syntax_institut.musicapp.ui.screens.SearchScreen
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme
import de.syntax_institut.musicapp.ui.viewModel.PlayerViewModel
import de.syntax_institut.musicapp.ui.viewModel.SongListViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AppStart() {
    val navController = rememberNavController()
    var isDarkTheme by remember { mutableStateOf(false) }
    var isMinimized by remember { mutableStateOf(false) }

    val songListViewModel: SongListViewModel = viewModel()
    val playerViewModel: PlayerViewModel = viewModel()

    val currentSongId by songListViewModel.currentSongId.collectAsState()
    val isPlaying     by playerViewModel.isPlaying.collectAsState()

    MusicAppTheme(darkTheme = isDarkTheme) {
        Scaffold(
            bottomBar = {
                // Mini-Player anzeigen, sobald ein Song abgespielt wird und man entweder zurück oder minimieren auswählt
                if (isMinimized && currentSongId != null) {
                    BottomPlayerBar(
                        onExpand = {
                            isMinimized = false
                            navController.navigate(AppDestinations.PlayerRoute(currentSongId!!))
                        },
                        songListViewModel = songListViewModel,
                        playerViewModel   = playerViewModel,
                        isDarkTheme       = isDarkTheme
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController    = navController,
                startDestination = AppDestinations.Home,
                modifier         = Modifier.padding(innerPadding)
            ) {
                // Home-Screen
                composable(AppDestinations.Home) {
                    HomeScreen(
                        onNavigateToSearch  = { navController.navigate(AppDestinations.Search) },
                        onNavigateToProfile = { navController.navigate(AppDestinations.Profile) },
                        onPlaySong          = { id ->
                            songListViewModel.selectSong(id)
                            isMinimized = false
                            navController.navigate(AppDestinations.PlayerRoute(id))
                        },
                        songListViewModel = songListViewModel,
                        isDarkTheme       = isDarkTheme
                    )
                }

                // Search-Screen
                composable(AppDestinations.Search) {
                    SearchScreen(
                        onPlaySong        = { id ->
                            songListViewModel.selectSong(id)
                            isMinimized = false
                            navController.navigate(AppDestinations.PlayerRoute(id))
                        },
                        navController     = navController,
                        songListViewModel = songListViewModel
                    )
                }

                // Profile-Screen
                composable(AppDestinations.Profile) {
                    ProfileScreen(
                        onBackClick   = { navController.popBackStack() },
                        isDarkTheme   = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme }
                    )
                }

                // Player-Screen mit Parameter songId
                composable(
                    route     = AppDestinations.Player,
                    arguments = AppDestinations.playerArguments
                ) { backStackEntry ->
                    val args = backStackEntry.toPlayerRouteModel()
                    PlayerScreen(
                        songId            = args.songId,
                        navController     = navController,
                        songListViewModel = songListViewModel,
                        playerViewModel   = playerViewModel,
                        onMinimize        = { isMinimized = true }
                    )
                }
            }
        }
    }
}