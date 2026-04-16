package com.novikon.tic_tac_toe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.novikon.tic_tac_toe.ui.theme.NeonCyan
import com.novikon.tic_tac_toe.ui.theme.NeonMagenta
import kotlin.math.roundToInt

// Component de la peça que el jugador pot arrossegar fins a una cel·la del tauler
@Composable
fun DraggablePiece(
    piece: String,               // "X" o "O"
    isCurrentTurn: Boolean,      // Indica si és el torn d'aquest jugador
    onDragEnd: (Offset) -> Unit  // Callback amb la posició final de la peça en pantalla
) {
    // Quantitat de desplaçament acumulat durant l'arrossegament
    var dragAmount by remember { mutableStateOf(Offset.Zero) }

    // Indica si l'usuari està arrossegant la peça en aquest moment
    var isDragging by remember { mutableStateOf(false) }

    // Posició inicial de la peça abans de començar a arrossegar
    // IMPORTANT: només s'actualitza quan NO estem arrossegant,
    // per evitar el doble comptatge de coordenades
    var initialPosition by remember { mutableStateOf(Offset.Zero) }

    // Si no és el torn d'aquest jugador, no mostrem la peça
    if (!isCurrentTurn) return

    Box(
        modifier = Modifier
            // Desplacem visualment la peça en funció de l'arrossegament
            .offset { IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt()) }
            .size(80.dp)
            // Ombra més gran quan s'arrossega, per donar sensació d'elevació
            .shadow(if (isDragging) 20.dp else 10.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .border(2.dp, if (piece == "X") NeonCyan else NeonMagenta, RoundedCornerShape(12.dp))
            .onGloballyPositioned { coordinates ->
                // Guardem la posició de la peça NOMÉS quan està en repòs
                // Si ho féssim durant l'arrossegament, la posició ja inclouria
                // el desplaçament i el sumaríem dues vegades al final
                if (!isDragging) {
                    initialPosition = coordinates.positionInRoot()
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        // Marquem que ha començat l'arrossegament
                        isDragging = true
                    },
                    onDrag = { change, dragDelta ->
                        change.consume() // Consumim l'event perquè no el processi cap altre element
                        // Acumulem el desplaçament total de l'arrossegament
                        dragAmount = Offset(
                            dragAmount.x + dragDelta.x,
                            dragAmount.y + dragDelta.y
                        )
                    },
                    onDragEnd = {
                        isDragging = false

                        // Calculem la posició final del centre de la peça:
                        // posició inicial (fixa) + desplaçament total + meitat de la mida de la peça
                        val finalX = initialPosition.x + dragAmount.x + size.width / 2f
                        val finalY = initialPosition.y + dragAmount.y + size.height / 2f

                        // Notifiquem al GameScreen on ha acabat la peça
                        onDragEnd(Offset(finalX, finalY))

                        // Reposem la peça a la seva posició original
                        dragAmount = Offset.Zero
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Text de la peça (X o O) amb el color corresponent
        Text(
            text = piece,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (piece == "X") NeonCyan else NeonMagenta
        )
    }
}