package com.example.tresenraya
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tresenraya.R
import com.example.tresenraya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var celdas: List<ImageView>

    private var turnoJugador = "esqueleto" // Comienza después de las calabazas
    private var esqueletoWins = 0
    private var brujaWins = 0
    private var tablero = Array(3) { arrayOfNulls<String>(3) } // Array 3x3 para marcar jugadas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        celdas = listOf(
            binding.imageView00, binding.imageView01, binding.imageView02,
            binding.imageView10, binding.imageView11, binding.imageView12,
            binding.imageView20, binding.imageView21, binding.imageView22
        )
        binding.resetButton.setOnClickListener {
            reiniciarTablero()
        }

        // Inicializa el tablero con calabazas y listeners de clic
        for ((index, cell) in celdas.withIndex()) {
            cell.setImageResource(R.drawable.calabaza)
            cell.setOnClickListener {
                marcarCelda(cell, index)
            }
        }

    }


    private fun marcarCelda(cell: ImageView, index: Int) {
        val row = index / 3
        val col = index % 3

        // Solo marca si está vacío
        if (tablero[row][col] == null) {
            if (turnoJugador == "esqueleto") {
                cell.setImageResource(R.drawable.esqueleto)
                tablero[row][col] = "esqueleto"
                turnoJugador = "bruja"
            } else {
                cell.setImageResource(R.drawable.bruja)
                tablero[row][col] = "bruja"
                turnoJugador = "esqueleto"
            }

            // Verificar si alguien ha ganado
            val ganador = checkWinner()
            if (ganador != null) {
                mostrarGanador(ganador)
                reiniciarTablero()
            }
        }
    }

    private fun checkWinner(): String? {
        // Combinaciones ganadoras
        val combinaciones = listOf(
            listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2)), // Primera fila
            listOf(Pair(1, 0), Pair(1, 1), Pair(1, 2)), // Segunda fila
            listOf(Pair(2, 0), Pair(2, 1), Pair(2, 2)), // Tercera fila
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0)), // Primera columna
            listOf(Pair(0, 1), Pair(1, 1), Pair(2, 1)), // Segunda columna
            listOf(Pair(0, 2), Pair(1, 2), Pair(2, 2)), // Tercera columna
            listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2)), // Diagonal principal
            listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))  // Diagonal secundaria
        )

        for (combinacion in combinaciones) {
            val (a, b, c) = combinacion
            if (tablero[a.first][a.second] != null &&
                tablero[a.first][a.second] == tablero[b.first][b.second] &&
                tablero[a.first][a.second] == tablero[c.first][c.second]
            ) {
                return tablero[a.first][a.second] // Retorna el jugador ganador
            }
        }
        return null // No hay ganador
    }

    private fun mostrarGanador(ganador: String) {
        // Actualiza el marcador
        if (ganador == "esqueleto") esqueletoWins++ else brujaWins++

        // Muestra el ganador y el marcador
        AlertDialog.Builder(this)
            .setTitle("¡Tenemos un ganador!")
            .setMessage("El ganador es: $ganador\n\nMarcador:\nEsqueleto: $esqueletoWins\nBruja: $brujaWins")
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    private fun reiniciarTablero() {
        // Limpia el tablero visual y el array de estado
        for (cell in celdas) {
            cell.setImageResource(R.drawable.calabaza)
        }
        tablero = Array(3) { arrayOfNulls<String>(3) }
        turnoJugador = "esqueleto" // Reinicia el turno
    }

}
