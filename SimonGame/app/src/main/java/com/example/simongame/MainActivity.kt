package com.example.simongame

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simongame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val colors = listOf("Red", "Green", "Blue", "Yellow")
    private val gameSequence = mutableListOf<String>()
    private val userSequence = mutableListOf<String>()
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Iniciar el juego
        addColorToSequence()

        // Configurar botones de colores
        binding.buttonRed.setOnClickListener { onUserInput("Red") }
        binding.buttonGreen.setOnClickListener { onUserInput("Green") }
        binding.buttonBlue.setOnClickListener { onUserInput("Blue") }
        binding.buttonYellow.setOnClickListener { onUserInput("Yellow") }

        // Configurar botón de reinicio
        binding.buttonRestart.setOnClickListener { restartGame() }
    }

    private fun addColorToSequence() {
        gameSequence.add(colors.random())
        showSequence()
        score++
        binding.scoreText.text = "Puntuacion: $score"
    }

    private fun showSequence() {
        userSequence.clear()
        for ((index, color) in gameSequence.withIndex()) {
            Handler().postDelayed({
                illuminateButton(color)
            }, 1000L * index)
        }
    }

    private fun illuminateButton(color: String) {
        val button = when (color) {
            "Red" -> binding.buttonRed
            "Green" -> binding.buttonGreen
            "Blue" -> binding.buttonBlue
            "Yellow" -> binding.buttonYellow
            else -> null
        }

        button?.let {
            it.setBackgroundColor(Color.LTGRAY)
            Handler().postDelayed({
                it.setBackgroundColor(Color.parseColor(color))
            }, 500)
        }
    }

    private fun onUserInput(color: String) {
        userSequence.add(color)
        for (i in userSequence.indices) {
            if (userSequence[i] != gameSequence[i]) {
                gameOver()
                return
            }
        }

        if (userSequence.size == gameSequence.size) {
            Toast.makeText(this, "Correcto! Siguiente ronda...", Toast.LENGTH_SHORT).show()
            addColorToSequence()
        }
    }

    private fun gameOver() {
        Toast.makeText(this, "Incorrecto! Volvamos a empezar...", Toast.LENGTH_SHORT).show()
        gameSequence.clear()
        userSequence.clear()
        score = 0
        binding.scoreText.text = "Puntuacion: $score"
        addColorToSequence()
    }

    private fun restartGame() {
        gameSequence.clear()
        userSequence.clear()
        score = 0
        binding.scoreText.text = "Puntuacion: $score"
        addColorToSequence()
    }
}
