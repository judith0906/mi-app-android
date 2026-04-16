package com.novikon.tic_tac_toe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.novikon.tic_tac_toe.ui.components.Cell
import com.novikon.tic_tac_toe.ui.components.DraggablePiece
import com.novikon.tic_tac_toe.ui.theme.NeonCyan
import com.novikon.tic_tac_toe.ui.theme.NeonMagenta
import com.novikon.tic_tac_toe.ui.theme.NeonYellow
import kotlinx.coroutines.delay

// Pantalla principal del joc on es desenvolupa la partida
@Composable
fun GameScreen(
    navController: NavController,
    player1Name: String,      // Nom del jugador 1 (X)
    player2Name: String,      // Nom del jugador 2 o "CPU"
    isSinglePlayer: Boolean   // True = vs CPU, False = dos jugadors
) {
    // Estat del tauler: llista de 9 cel·les, inicialment buides
    var cells by remember { mutableStateOf(List(9) { "" }) }

    // Jugador actual: "X" o "O"
    var currentPlayer by remember { mutableStateOf("X") }

    // Guanyador de la partida: null si no hi ha guanyador encara
    var winner by remember { mutableStateOf<String?>(null) }

    // Indica si la partida ha acabat en empat
    var isDraw by remember { mutableStateOf(false) }

    // Indica si la CPU està calculant la seva jugada (evita accions del jugador mentre espera)
    var isCpuThinking by remember { mutableStateOf(false) }

    // Estructura de dades que guarda els límits de cada cel·la a la pantalla
    // Necessari per detectar on cau la peça arrossegada
    data class CellBounds(
        val row: Int, val col: Int,
        val left: Float, val top: Float,
        val right: Float, val bottom: Float
    )
    var cellBoundsList by remember { mutableStateOf(listOf<CellBounds>()) }

    // Converteix fila i columna (0-2) a índex de la llista (0-8)
    fun rowColToIndex(row: Int, col: Int): Int = row * 3 + col

    // Comprova si hi ha un guanyador revisant tots els patrons guanyadors possibles
    fun checkWinner(): String? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Files
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columnes
            listOf(0, 4, 8), listOf(2, 4, 6)                   // Diagonals
        )
        for (pattern in winPatterns) {
            val a = cells[pattern[0]]
            val b = cells[pattern[1]]
            val c = cells[pattern[2]]
            // Si les tres cel·les del patró coincideixen i no estan buides, hi ha guanyador
            if (a != "" && a == b && b == c) return a
        }
        return null
    }

    // Comprova si la partida ha acabat en empat (totes les cel·les plenes i sense guanyador)
    fun checkDraw(): Boolean = cells.all { it != "" } && checkWinner() == null

    // Intenta fer una jugada a la cel·la indicada
    // Retorna true si la jugada és vàlida, false si no
    fun makeMove(index: Int, player: String): Boolean {
        // Validem que la jugada sigui possible
        if (winner != null || isDraw || cells[index] != "" || player != currentPlayer || isCpuThinking) {
            return false
        }

        // Actualitzem el tauler amb la nova jugada
        val newCells = cells.toMutableList()
        newCells[index] = player
        cells = newCells

        // Comprovem si aquesta jugada ha fet guanyar algú
        val winnerFound = checkWinner()
        if (winnerFound != null) {
            winner = winnerFound
            return true
        }

        // Comprovem empat
        if (checkDraw()) {
            isDraw = true
            return true
        }

        // Canviem de torn
        currentPlayer = if (currentPlayer == "X") "O" else "X"
        return true
    }

    // Reinicia tots els estats per començar una nova partida
    fun resetGame() {
        cells = List(9) { "" }
        currentPlayer = "X"
        winner = null
        isDraw = false
        isCpuThinking = false
    }

    // Busca quina cel·la correspon a les coordenades on ha caigut la peça arrossegada
    fun findCellByPosition(x: Float, y: Float): Pair<Int, Int>? {
        // Primer mirem si les coordenades cauen exactament dins d'alguna cel·la
        for (cell in cellBoundsList) {
            if (x >= cell.left && x <= cell.right && y >= cell.top && y <= cell.bottom) {
                return Pair(cell.row, cell.col)
            }
        }

        // Si no cau exactament a cap, busquem la cel·la més propera pel seu centre
        var closestCell: CellBounds? = null
        var minDistance = Float.MAX_VALUE

        for (cell in cellBoundsList) {
            val cellCenterX = (cell.left + cell.right) / 2
            val cellCenterY = (cell.top + cell.bottom) / 2
            val distance = kotlin.math.sqrt(
                (x - cellCenterX) * (x - cellCenterX) +
                        (y - cellCenterY) * (y - cellCenterY)
            )
            if (distance < minDistance) {
                minDistance = distance
                closestCell = cell
            }
        }

        return if (closestCell != null) Pair(closestCell.row, closestCell.col) else null
    }

    // Lògica de la CPU: s'executa quan és el torn de la CPU en mode 1 jugador
    LaunchedEffect(currentPlayer, isSinglePlayer, winner, isDraw) {
        if (isSinglePlayer && currentPlayer == "O" && winner == null && !isDraw && !isCpuThinking) {
            isCpuThinking = true
            delay(600) // Pausa per simular que la CPU "pensa"

            // La CPU tria una cel·la buida aleatòriament
            val emptyIndices = cells.indices.filter { cells[it] == "" }
            if (emptyIndices.isNotEmpty()) {
                val randomIndex = emptyIndices.random()
                val newCells = cells.toMutableList()
                newCells[randomIndex] = "O"
                cells = newCells

                // Comprovem resultat després de la jugada de la CPU
                val winnerFound = checkWinner()
                when {
                    winnerFound != null -> winner = winnerFound
                    checkDraw()        -> isDraw = true
                    else               -> currentPlayer = "X" // Tornem el torn al jugador humà
                }
            }
            isCpuThinking = false
        }
    }

    // --- INTERFÍCIE D'USUARI ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Missatge d'estat: guanyador, empat o torn actual
            when {
                winner != null -> {
                    val winnerName  = if (winner == "X") player1Name else player2Name
                    val winnerColor = if (winner == "X") NeonCyan else NeonMagenta
                    Text(
                        text = "🏆 ¡$winnerName GANA! 🏆",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = winnerColor
                    )
                }
                isDraw -> {
                    Text(
                        text = "🤝 ¡EMPATE! 🤝",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonYellow
                    )
                }
                else -> {
                    val currentPlayerName = if (currentPlayer == "X") player1Name else player2Name
                    val currentColor = if (currentPlayer == "X") NeonCyan else NeonMagenta
                    Text(
                        text = "Turno de: $currentPlayerName",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = currentColor
                    )
                    Text(
                        text = "Arrastra la ficha al tablero",
                        fontSize = 14.sp,
                        color = currentColor.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tauler de joc: 3 files x 3 columnes de cel·les
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in 0..2) {
                    Row {
                        for (col in 0..2) {
                            val index = rowColToIndex(row, col)
                            Cell(
                                value = cells[index],
                                onCellClick = {
                                    // Permet jugar fent clic directe (alternativa a l'arrossegament)
                                    if (winner == null && !isDraw) {
                                        if (isSinglePlayer) {
                                            if (currentPlayer == "X") makeMove(index, "X")
                                        } else {
                                            makeMove(index, currentPlayer)
                                        }
                                    }
                                },
                                onBoundsReady = { left, top, right, bottom ->
                                    // Actualitzem els límits d'aquesta cel·la a la llista
                                    val newBounds = cellBoundsList.filter { it.row != row || it.col != col } +
                                            CellBounds(row, col, left, top, right, bottom)
                                    cellBoundsList = newBounds
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Peça arrossegable: només es mostra si la partida no ha acabat
            if (winner == null && !isDraw) {
                if (isSinglePlayer) {
                    // En mode 1 jugador, la peça arrossegable només apareix quan és el torn del jugador humà (X)
                    if (currentPlayer == "X") {
                        DraggablePiece(
                            piece = "X",
                            isCurrentTurn = true,
                            onDragEnd = { dropPosition ->
                                val cell = findCellByPosition(dropPosition.x, dropPosition.y)
                                if (cell != null) {
                                    val index = rowColToIndex(cell.first, cell.second)
                                    if (cells[index] == "") makeMove(index, "X")
                                }
                            }
                        )
                    }
                } else {
                    // En mode 2 jugadors, la peça arrossegable canvia entre X i O cada torn
                    DraggablePiece(
                        piece = currentPlayer,
                        isCurrentTurn = true,
                        onDragEnd = { dropPosition ->
                            val cell = findCellByPosition(dropPosition.x, dropPosition.y)
                            if (cell != null) {
                                val index = rowColToIndex(cell.first, cell.second)
                                if (cells[index] == "") makeMove(index, currentPlayer)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botons d'acció: reiniciar partida i tornar al menú principal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { resetGame() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A))
                ) {
                    Text("🔄 Reiniciar", color = NeonCyan)
                }
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A))
                ) {
                    Text("🏠 Salir", color = NeonMagenta)
                }
            }
        }
    }
}