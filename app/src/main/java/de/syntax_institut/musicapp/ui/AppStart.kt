package de.syntax_institut.musicapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.syntax_institut.musicapp.ui.navigation.HomeRoute
import de.syntax_institut.musicapp.ui.navigation.PlayerRoute
import de.syntax_institut.musicapp.ui.navigation.ProfileRoute
import de.syntax_institut.musicapp.ui.navigation.SearchRoute
import de.syntax_institut.musicapp.ui.screens.HomeScreen
import de.syntax_institut.musicapp.ui.screens.PlayerScreen
import de.syntax_institut.musicapp.ui.screens.ProfileScreen
import de.syntax_institut.musicapp.ui.screens.SearchScreen
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme
import de.syntax_institut.musicapp.ui.viewModel.SongViewModel

@Composable
fun AppStart() {
    val navController = rememberNavController()
    var isDarkTheme by remember { mutableStateOf(false) }

    MusicAppTheme(darkTheme = isDarkTheme) {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HomeRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<HomeRoute> {
                    HomeScreen(
                        onPlaySong = { id ->
                            navController.navigate(PlayerRoute(id))
                        },
                        onNavigateToProfile = {
                            navController.navigate(ProfileRoute)
                        },
                        onNavigateToSearch = {
                            navController.navigate(SearchRoute)
                        },
                        viewModel = SongViewModel(),
                        isDarkTheme = isDarkTheme
                    )
                }

                composable<SearchRoute> {
                    SearchScreen(
                        onPlaySong = { id ->
                            navController.navigate(PlayerRoute(id))
                        },
                        navController = navController,
                        viewModel = viewModel()
                    )
                }
                composable<ProfileRoute> {
                    ProfileScreen(
                        onBackClick = { navController.popBackStack() },
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme }
                    )
                }
                composable<PlayerRoute> { backStackEntry ->
                    val args = backStackEntry.toRoute<PlayerRoute>()
                    PlayerScreen(
                        songId = args.songId,
                        navController = navController,
                        viewModel = viewModel()
                    )
                }
            }
        }
    }
}
