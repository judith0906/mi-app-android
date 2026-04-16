package com.novikon.tic_tac_toe.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Definició dels colors neon principals del joc
val NeonCyan    = Color(0xFF00FFFF) // Color del jugador X
val NeonMagenta = Color(0xFFFF00FF) // Color del jugador O
val NeonYellow  = Color(0xFFFFFF00) // Color per empats i títols
val DarkBackground = Color(0xFF000000) // Fons negre
val DarkGray    = Color(0xFF1A1A1A)    // Gris fosc per a targetes i botons

// Esquema de colors fosc personalitzat amb els colors neon
private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonMagenta,
    tertiary = NeonYellow,
    background = DarkBackground,
    surface = DarkGray,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = NeonCyan,
    onSurface = NeonCyan
)

// Tema principal de l'app, sempre en mode fosc
@Composable
fun TicTacToeTheme(
    darkTheme: Boolean = true,       // Forcem sempre el tema fosc
    dynamicColor: Boolean = false,   // Desactivem els colors dinàmics del sistema
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}