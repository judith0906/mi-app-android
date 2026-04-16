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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.novikon.tic_tac_toe.ui.navigation.Routes
import com.novikon.tic_tac_toe.ui.theme.NeonCyan
import com.novikon.tic_tac_toe.ui.theme.NeonMagenta

// Pantalla principal (menú d'inici) de l'aplicació
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Títol del joc en tres línies amb colors neon alternats
        Text(
            text = "TIC",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = NeonCyan,
            letterSpacing = 8.sp
        )
        Text(
            text = "TAC",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = NeonMagenta,
            letterSpacing = 8.sp
        )
        Text(
            text = "TOE",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 8.sp
        )

        Spacer(modifier = Modifier.height(64.dp))

        // Botó per anar a la pantalla de configuració de la partida
        Button(
            onClick = { navController.navigate(Routes.CONFIG) },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Text(
                text = "🎮 JUGAR",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botó per anar a la pantalla de crèdits
        Button(
            onClick = { navController.navigate(Routes.CREDITS) },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Text(
                text = "📋 CRÉDITOS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = NeonMagenta
            )
        }
    }
}