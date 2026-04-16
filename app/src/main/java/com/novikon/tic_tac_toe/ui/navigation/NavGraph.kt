package com.novikon.tic_tac_toe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import com.novikon.tic_tac_toe.ui.screens.*

// Funció que defineix tota la navegació de l'app
@Composable
fun AppNavigation() {

    // Controlador de navegació que gestiona la pila de pantalles
    val navController = rememberNavController()

    // Contenidor de navegació, la pantalla inicial és HOME
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // Ruta cap a la pantalla d'inici
        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        // Ruta cap a la pantalla de crèdits
        composable(Routes.CREDITS) {
            CreditsScreen(navController)
        }

        // Ruta cap a la pantalla de configuració
        composable(Routes.CONFIG) {
            ConfigScreen(navController)
        }

        // Ruta cap al joc, amb paràmetres de tipus String i Boolean
        composable(
            route = "game/{player1}/{player2}/{isSinglePlayer}",
            arguments = listOf(
                navArgument("player1") { type = NavType.StringType },
                navArgument("player2") { type = NavType.StringType },
                navArgument("isSinglePlayer") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            // Recuperem els paràmetres passats per la navegació
            val player1 = backStackEntry.arguments?.getString("player1") ?: "Jugador 1"
            val player2 = backStackEntry.arguments?.getString("player2") ?: "Jugador 2"
            val isSinglePlayer = backStackEntry.arguments?.getBoolean("isSinglePlayer") ?: true

            // Passem els paràmetres a la pantalla de joc
            GameScreen(
                navController = navController,
                player1Name = player1,
                player2Name = player2,
                isSinglePlayer = isSinglePlayer
            )
        }
    }
}