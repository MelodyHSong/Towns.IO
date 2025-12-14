/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: HighScoreEntry.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

// ☆ Description: Estructura de un registro de alta puntuación, incluye dificultad, tiempo y modo de juego.

data class HighScoreEntry(
    val userName: String = "Player",                    // Nombre del jugador
    val score: Int = 0,                                 // Número de respuestas correctas
    val guesses: Int = 0,                               // Total de intentos
    val difficulty: String = Difficulty.EASY.name,      // Nivel de dificultad
    val mode: String = GameMode.FLAG_QUIZ.name,         // Modo de juego
    val timeMillis: Long = 999999999L,                  // Tiempo total tomado en milisegundos
    val timestamp: Long = System.currentTimeMillis()    // Momento en que se registró la puntuación
)