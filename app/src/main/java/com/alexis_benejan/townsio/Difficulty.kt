/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: Difficulty.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

// ☆ Description: Define los niveles de dificultad y el número total de preguntas para cada uno.

enum class Difficulty(val maxQuestions: Int) {
    EASY(10),       // Fácil: 10 preguntas
    HARD(35),       // Difícil: 35 preguntas
    PATRIOT(78)     // Patriota: 78 preguntas (todos los municipios)
}