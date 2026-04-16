package com.novikon.tic_tac_toe.ui.navigation

// Objecte que centralitza totes les rutes de navegació de l'app
object Routes {
    const val HOME = "home"       // Pantalla d'inici
    const val CREDITS = "credits" // Pantalla de crèdits
    const val CONFIG = "config"   // Pantalla de configuració de partida

    // Ruta del joc amb paràmetres: noms dels jugadors i mode de joc
    const val GAME = "game/{player1}/{player2}/{isSinglePlayer}"
}