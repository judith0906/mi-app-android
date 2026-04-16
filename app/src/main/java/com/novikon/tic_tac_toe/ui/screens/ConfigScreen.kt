package com.novikon.tic_tac_toe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.novikon.tic_tac_toe.ui.theme.NeonCyan
import com.novikon.tic_tac_toe.ui.theme.NeonMagenta

// Pantalla de configuració on es tria el mode de joc i els noms dels jugadors
@Composable
fun ConfigScreen(navController: NavController) {

    // Mode seleccionat: null = cap, "1player" = vs CPU, "2player" = dos jugadors
    var selectedMode by remember { mutableStateOf<String?>(null) }

    // Noms dels jugadors introduïts als camps de text
    var player1 by remember { mutableStateOf("") }
    var player2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Títol de la pantalla
        Text(
            text = "CONFIGURACIÓN",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = NeonCyan,
            letterSpacing = 4.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botó per seleccionar el mode d'1 jugador contra la CPU
        Button(
            onClick = {
                selectedMode = "1player"
                player2 = "CPU" // El segon jugador sempre és la CPU en aquest mode
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(12.dp),
            // El botó seleccionat canvia de color per indicar que està actiu
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedMode == "1player") NeonCyan else Color(0xFF1A1A1A),
                contentColor   = if (selectedMode == "1player") Color.Black else NeonCyan
            )
        ) {
            Text(text = "🎮 1 JUGADOR (VS CPU)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botó per seleccionar el mode de 2 jugadors locals
        Button(
            onClick = {
                selectedMode = "2player"
                player2 = "" // Buidem el nom del segon jugador per introduir-ne un de nou
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedMode == "2player") NeonMagenta else Color(0xFF1A1A1A),
                contentColor   = if (selectedMode == "2player") Color.Black else NeonMagenta
            )
        ) {
            Text(text = "👥 2 JUGADORES", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Camp de text per al nom del jugador 1 (sempre visible)
        OutlinedTextField(
            value = player1,
            onValueChange = { player1 = it },
            label = { Text("Nombre Jugador 1 (X)", color = NeonCyan) },
            placeholder = { Text("Ej: Juan", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = NeonCyan,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor     = NeonCyan,
                unfocusedTextColor   = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Camp de text per al nom del jugador 2 (només visible en mode 2 jugadors)
        if (selectedMode == "2player") {
            OutlinedTextField(
                value = player2,
                onValueChange = { player2 = it },
                label = { Text("Nombre Jugador 2 (O)", color = NeonMagenta) },
                placeholder = { Text("Ej: María", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = NeonMagenta,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor     = NeonMagenta,
                    unfocusedTextColor   = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // El botó de començar només s'activa si s'ha triat un mode i hi ha nom del jugador 1
        val isReady = selectedMode != null && player1.isNotBlank()

        Button(
            onClick = {
                val p1 = player1
                val p2 = when (selectedMode) {
                    "1player" -> "CPU"
                    else -> if (player2.isBlank()) "Jugador 2" else player2
                }
                val isSingle = selectedMode == "1player"
                // Naveguem a la pantalla de joc passant els paràmetres per la URL
                navController.navigate("game/$p1/$p2/$isSingle")
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = isReady,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isReady) NeonCyan else Color.Gray,
                contentColor   = Color.Black
            )
        ) {
            Text(text = "🚀 COMENZAR PARTIDA", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}