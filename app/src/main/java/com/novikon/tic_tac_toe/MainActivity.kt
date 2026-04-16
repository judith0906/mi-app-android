package com.novikon.tic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.novikon.tic_tac_toe.ui.navigation.AppNavigation
import com.novikon.tic_tac_toe.ui.theme.TicTacToeTheme

// Activitat principal de l'aplicació, punt d'entrada quan s'obre l'app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                //Això és una prova per a DMIC desde Github
                // Carrega el sistema de navegació entre pantalles
                AppNavigation()
            }
        }
    }
}
