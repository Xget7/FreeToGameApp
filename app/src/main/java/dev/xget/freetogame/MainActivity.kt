package dev.xget.freetogame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.xget.freetogame.domain.model.game.FreeGame
import dev.xget.freetogame.presentation.games.details.GameDetailsScreen
import dev.xget.freetogame.presentation.games.details.GameDetailsViewModel
import dev.xget.freetogame.presentation.games.home.HomeScreen
import dev.xget.freetogame.presentation.games.home.HomeScreenViewModel
import dev.xget.freetogame.presentation.utils.ScreensRoutes.DETAILS_SCREEN
import dev.xget.freetogame.presentation.utils.ScreensRoutes.HOME_SCREEN
import dev.xget.freetogame.presentation.utils.viewModelFactory
import dev.xget.freetogame.ui.theme.FreetoGameTheme
import kotlin.random.Random


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreetoGameTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = HOME_SCREEN) {
                    // ...
                    composable(HOME_SCREEN) { navBackStackEntry ->
                        HomeScreen(
                            navController = navController
                        )
                    }
                    composable("$DETAILS_SCREEN/{freeGameId}") { navBackStackEntry ->

                        GameDetailsScreen(nav = navController)
                    }
                }
            }
        }
    }
}
