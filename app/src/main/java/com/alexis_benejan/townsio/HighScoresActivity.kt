/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: HighScoresActivity.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexis_benejan.townsio.databinding.ActivityHighScoresBinding
import android.media.MediaPlayer
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.TextView

// ☆ Description: Muestra los resultados del quiz, el récord histórico y la lista Top 5, con filtros por Modo y Dificultad.

class HighScoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHighScoresBinding
    private lateinit var highScoresManager: HighScoresManager
    private var mediaPlayer: MediaPlayer? = null

    // Variables de filtrado: Ambas son necesarias para el leaderboard
    private lateinit var currentViewingDifficulty: Difficulty
    private lateinit var currentViewingMode: GameMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHighScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.quiz_results)

        highScoresManager = HighScoresManager(this)
        mediaPlayer = MediaPlayer()

        // 1. Determinar el Modo y la Dificultad iniciales a mostrar (de la partida recién terminada)

        // Obtener Dificultad (usa EASY como respaldo)
        val lastDifficultyName = intent.getStringExtra("DIFFICULTY_MODE") ?: Difficulty.EASY.name
        currentViewingDifficulty = try {
            Difficulty.valueOf(lastDifficultyName)
        } catch (e: IllegalArgumentException) {
            Difficulty.EASY
        }

        // Obtener Modo (usa FLAG_QUIZ como respaldo)
        val lastModeName = intent.getStringExtra("GAME_MODE") ?: GameMode.FLAG_QUIZ.name
        currentViewingMode = try {
            GameMode.valueOf(lastModeName)
        } catch (e: IllegalArgumentException) {
            GameMode.FLAG_QUIZ
        }

        // 2. Configurar los selectores
        setupModeSelector()
        setupDifficultySelector()

        // 3. Mostrar la puntuación de la partida (si existe)
        displayCurrentResults()

        // 4. Mostrar los récords con el doble filtro
        displayRecord()
        displayTopFiveScores()

        // 5. Configurar el botón de reinicio
        setupRestartButton()
    }

    // ☆ Description: Convierte milisegundos a formato MM:SS.
    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    // ☆ Description: Configura el selector para cambiar el MODO de juego a visualizar.
    private fun setupModeSelector() {
        // 1. Obtener y mostrar el nombre CORTO amigable en el selector
        val currentModeShortTitle = getString(currentViewingMode.shortTitleResId)
        binding.tvModeSelector.text = getString(R.string.records_display_mode, currentModeShortTitle)

        // 2. Preparar el diálogo con nombres LARGOS (para mayor claridad)
        val modes = GameMode.entries.toTypedArray()
        val modeTitles = modes.map { getString(it.titleResId) }.toTypedArray() // Usa el título largo en el diálogo

        binding.tvModeSelector.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.select_mode_view))
                .setItems(modeTitles) { dialog, which ->
                    currentViewingMode = modes[which] // Selecciona el enum por índice
                    // Recargar UI con el nuevo MODO
                    displayRecord()
                    displayTopFiveScores()
                }
                .show()
        }
    }

    // ☆ Description: Configura el selector para cambiar la DIFICULTAD de visualización.
    private fun setupDifficultySelector() {
        // Muestra la dificultad actual en el TextView (usa el nombre del enum)
        binding.tvDifficultySelector.text = getString(R.string.record_display_difficulty, currentViewingDifficulty.name)

        val difficulties = Difficulty.entries.toTypedArray()
        val difficultyNames = difficulties.map { it.name }.toTypedArray()

        binding.tvDifficultySelector.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.select_difficulty_view))
                .setItems(difficultyNames) { dialog, which ->
                    currentViewingDifficulty = difficulties[which]
                    // Recargar UI con la nueva DIFICULTAD
                    displayRecord()
                    displayTopFiveScores()
                }
                .show()
        }
    }

    // ☆ Description: Muestra la puntuación del juego recién terminado y reproduce sonido si se ha establecido un nuevo récord.
    private fun displayCurrentResults() {
        val correct = intent.getIntExtra("SCORE_CORRECT", -1)
        val guesses = intent.getIntExtra("SCORE_TOTAL_GUESSES", -1)
        val newRecordSet = intent.getBooleanExtra("NEW_RECORD", false)

        if (correct >= 0 && guesses > 0) {
            val percentage = (correct.toDouble() / guesses) * 100

            binding.tvCurrentScore.text = getString(R.string.score_summary_format, guesses, percentage)

            if (newRecordSet) {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(this, R.raw.high_score)
                mediaPlayer?.start()
            }
        } else {
            binding.tvCurrentScore.text = ""
            binding.tvCurrentScore.visibility = View.GONE
        }
    }

    // ☆ Description: Muestra el récord (mejor puntuación) para el modo y dificultad actualmente seleccionados.
    private fun displayRecord() {
        // Obtener título CORTO legible del modo para la UI
        val currentModeShortTitle = getString(currentViewingMode.shortTitleResId)

        // Actualizar la visualización de los filtros
        binding.tvModeSelector.text = getString(R.string.records_display_mode, currentModeShortTitle)
        binding.tvDifficultySelector.text = getString(R.string.record_display_difficulty, currentViewingDifficulty.name)

        // Llama al manager con AMBOS filtros
        val recordEntry = highScoresManager.getTopFiveScores(currentViewingMode, currentViewingDifficulty).firstOrNull()

        if (recordEntry != null) {
            val timeString = formatTime(recordEntry.timeMillis)
            // Formato que incluye Score, Guesses, Tiempo y Nombre
            val scoreText = "${recordEntry.score}/${recordEntry.guesses} en $timeString por ${recordEntry.userName}"
            binding.tvMaxScoreValue.text = scoreText
        } else {
            binding.tvMaxScoreValue.text = getString(R.string.no_record_found)
        }
    }

    // ☆ Description: Muestra la lista del Top 5 de puntuaciones filtrada por modo y dificultad.
    private fun displayTopFiveScores() {
        // Llama al manager con AMBOS filtros
        val topScores = highScoresManager.getTopFiveScores(currentViewingMode, currentViewingDifficulty)
        val stringBuilder = StringBuilder()

        if (topScores.isEmpty()) {
            // Muestra mensaje genérico para la dificultad actual
            stringBuilder.append(getString(R.string.no_scores_recorded_difficulty, currentViewingDifficulty.name))
        } else {
            topScores.forEachIndexed { index, entry ->
                val timeString = formatTime(entry.timeMillis)

                stringBuilder.append(
                    getString(
                        R.string.score_list_item_format_time,
                        index + 1,
                        entry.userName,
                        entry.score,
                        entry.guesses,
                        timeString
                    )
                )
                stringBuilder.append("\n")
            }
        }
        binding.tvHighScoreList.text = stringBuilder.toString()
    }

    // ☆ Description: Regresa al menú principal (MainActivity) y limpia la pila de actividades.
    private fun setupRestartButton() {
        binding.btnRestartApp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}