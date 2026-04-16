package com.novikon.tic_tac_toe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.novikon.tic_tac_toe.ui.theme.NeonCyan
import com.novikon.tic_tac_toe.ui.theme.NeonMagenta

// Component que representa una cel·la individual del tauler (3x3 = 9 cel·les en total)
@Composable
fun Cell(
    value: String,         // Contingut de la cel·la: "X", "O" o "" (buida)
    onCellClick: () -> Unit, // Callback quan l'usuari fa clic directe sobre la cel·la
    onBoundsReady: (left: Float, top: Float, right: Float, bottom: Float) -> Unit // Callback que informa de la posició de la cel·la a la pantalla
) {
    Box(
        modifier = Modifier
            .size(100.dp)    // Mida fixa de cada cel·la
            .padding(6.dp)   // Separació entre cel·les
            .background(Color.Black)
            .border(
                width = 2.dp,
                // El color de la vora canvia segons si hi ha X, O o està buida
                color = when {
                    value == "X" -> NeonCyan
                    value == "O" -> NeonMagenta
                    else -> Color.DarkGray
                },
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCellClick() }
            .onGloballyPositioned { coordinates ->
                // Cada vegada que la cel·la es posiciona a la pantalla,
                // calculem els seus límits en coordenades absolutes (root)
                // per poder detectar si una peça arrossegada hi cau al damunt
                val position = coordinates.positionInRoot()
                val left   = position.x
                val top    = position.y
                val right  = position.x + coordinates.size.width
                val bottom = position.y + coordinates.size.height
                onBoundsReady(left, top, right, bottom)
            },
        contentAlignment = Alignment.Center
    ) {
        // Mostrem X o O segons el valor, amb el color corresponent
        when (value) {
            "X" -> Text(
                text = "X",
                color = NeonCyan,
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold
            )
            "O" -> Text(
                text = "O",
                color = NeonMagenta,
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold
            )
            // Si la cel·la està buida, no mostrem res
        }
    }
}