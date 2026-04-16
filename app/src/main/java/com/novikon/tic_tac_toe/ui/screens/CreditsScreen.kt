package com.novikon.tic_tac_toe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.novikon.tic_tac_toe.ui.theme.NeonCyan
import com.novikon.tic_tac_toe.ui.theme.NeonMagenta
import com.novikon.tic_tac_toe.ui.theme.NeonYellow

// Pantalla de crèdits de l'aplicació
@Composable
fun CreditsScreen(navController: NavController) {
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
            text = "CRÉDITOS",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = NeonYellow,
            letterSpacing = 4.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Targeta amb la informació dels crèdits
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Etiqueta de la secció de desenvolupadora
                Text(
                    text = "👨‍💻 Desarrollado por:",
                    fontSize = 18.sp,
                    color = NeonCyan
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Nom de la desenvolupadora, centrat correctament
                Text(
                    text = "Judith Carballido Martínez",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Etiqueta de l'assignatura
                Text(
                    text = "📱 Proyecto",
                    fontSize = 18.sp,
                    color = NeonMagenta
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Nom del projecte
                Text(
                    text = "Tic Tac Toe",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Botó per tornar a la pantalla anterior
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Text(
                text = "◀ VOLVER",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )
        }
    }
}