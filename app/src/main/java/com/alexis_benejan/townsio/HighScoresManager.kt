/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: HighScoresManager.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.max

// ☆ Description: Gestiona el almacenamiento y recuperación de las puntuaciones altas, aplicando filtros por modo, dificultad y tiempo.

class HighScoresManager(context: Context) {
    private val PREFS_FILE_NAME = "TownsIO_HighScores"
    private val KEY_SCORE_LIST = "HighScoresList"
    private val KEY_MAX_SCORE = "MaxScore"

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    // ☆ Description: Guarda una nueva puntuación, manteniendo la lista ordenada por score, modo, dificultad y tiempo.
    fun saveNewScore(newEntry: HighScoreEntry): Boolean {
        val currentScores = getHighScores()

        val combinedScores = currentScores + newEntry

        // Criterios de Ordenación: Score (Mayor), Modo, Dificultad, TimeMillis (Menor)
        val updatedScores = combinedScores
            .sortedWith(compareByDescending<HighScoreEntry> { it.score }
                .thenBy { it.mode }
                .thenBy { it.difficulty }
                .thenBy { it.timeMillis }
                .thenBy { it.guesses }
                .thenByDescending { it.timestamp }
            )
            .take(10) // Mantiene solo el Top 10

        // Guarda la lista actualizada
        val jsonString = gson.toJson(updatedScores)
        sharedPrefs.edit().putString(KEY_SCORE_LIST, jsonString).apply()

        // Verificación de Récord Máximo Histórico General
        val currentMaxScore = getMaxScore()
        val newMaxScore = max(currentMaxScore, newEntry.score)

        if (newMaxScore > currentMaxScore) {
            sharedPrefs.edit().putInt(KEY_MAX_SCORE, newMaxScore).apply()
            return true
        }
        return false
    }

    // ☆ Description: Recupera la lista completa de las puntuaciones guardadas.
    fun getHighScores(): List<HighScoreEntry> {
        val jsonString = sharedPrefs.getString(KEY_SCORE_LIST, null)
        return if (jsonString != null) {
            val type = object : TypeToken<List<HighScoreEntry>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            emptyList()
        }
    }

    // ☆ Description: Devuelve las 5 mejores puntuaciones para un modo y dificultad específicos.
    fun getTopFiveScores(mode: GameMode, difficulty: Difficulty): List<HighScoreEntry> {
        return getHighScores()
            .filter { it.mode == mode.name && it.difficulty == difficulty.name }
            .take(5)
    }

    // ☆ Description: Recupera el puntaje máximo histórico general (sin filtros).
    fun getMaxScore(): Int {
        return sharedPrefs.getInt(KEY_MAX_SCORE, 0)
    }
}