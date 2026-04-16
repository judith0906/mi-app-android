package com.novikon.tic_tac_toe.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

// Component que dibuixa una X amb Canvas, amb animació opcional de parpelleig
@Composable
fun XShape(color: Color, modifier: Modifier = Modifier, isAnimating: Boolean = false) {

    // Animació infinita que fa variar l'opacitat entre 0.5 i 1.0
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse // Va i torna d'opac a transparent
        )
    )

    Canvas(modifier = modifier) {
        // Gruix de la línia proporcional a la mida del component
        val strokeWidth = size.minDimension * 0.1f

        // Si isAnimating és true, apliquem l'alfa animat; si no, sempre opac
        val finalAlpha = if (isAnimating) alpha else 1f

        // Línia diagonal de dalt-esquerra a baix-dreta
        drawLine(
            color = color.copy(alpha = finalAlpha),
            start = Offset(size.width * 0.2f, size.height * 0.2f),
            end   = Offset(size.width * 0.8f, size.height * 0.8f),
            strokeWidth = strokeWidth
        )
        // Línia diagonal de dalt-dreta a baix-esquerra
        drawLine(
            color = color.copy(alpha = finalAlpha),
            start = Offset(size.width * 0.8f, size.height * 0.2f),
            end   = Offset(size.width * 0.2f, size.height * 0.8f),
            strokeWidth = strokeWidth
        )
    }
}