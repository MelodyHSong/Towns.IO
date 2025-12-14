/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: MainActivity.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alexis_benejan.townsio.databinding.ActivityMainBinding
import androidx.appcompat.app.AlertDialog

// ☆ Description: Menú principal que maneja la selección del Modo de Juego, la Dificultad, y la navegación a Récords y Acerca De.

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedMode: GameMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Configurar Listeners para los Botones de Modo
        binding.btnModeFlags.setOnClickListener {
            startDifficultySelection(GameMode.FLAG_QUIZ)
        }
        binding.btnModeShields.setOnClickListener {
            startDifficultySelection(GameMode.SHIELD_QUIZ)
        }
        binding.btnModeCognomentos.setOnClickListener {
            startDifficultySelection(GameMode.COGNOMENTO_QUIZ)
        }

        // 2. Configurar el Botón de High Scores
        binding.btnViewHighScores.setOnClickListener {
            viewHighScores()
        }

        // 3. Configurar el Botón Acerca De (Lanzará AboutActivity)
        binding.btnAboutApp.setOnClickListener {
            viewAbout()
        }
    }

    // ☆ Description: Muestra un diálogo para que el usuario seleccione la dificultad antes de iniciar el quiz.
    private fun startDifficultySelection(mode: GameMode) {
        selectedMode = mode

        val difficulties = Difficulty.entries.toTypedArray()

        // Muestra el nombre del enum como la opción legible en el diálogo.
        val difficultyNames = difficulties.map { it.name }.toTypedArray()

        // Crear el diálogo
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_difficulty))
            .setItems(difficultyNames) { dialog, which ->
                val selectedDifficulty = difficulties[which]
                launchQuizActivity(selectedDifficulty)
            }
            .setNegativeButton(getString(R.string.action_cancel), null)
            .show()
    }

    // ☆ Description: Inicia QuizActivity con el modo y dificultad seleccionados.
    private fun launchQuizActivity(difficulty: Difficulty) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            // Pasar los parámetros al QuizActivity
            putExtra("GAME_MODE", selectedMode.name)
            putExtra("DIFFICULTY_MODE", difficulty.name)
        }
        startActivity(intent)
    }

    // ☆ Description: Lanza HighScoresActivity.
    private fun viewHighScores() {
        val intent = Intent(this, HighScoresActivity::class.java)
        startActivity(intent)
    }

    // ☆ Description: Lanza AboutActivity.
    private fun viewAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
}