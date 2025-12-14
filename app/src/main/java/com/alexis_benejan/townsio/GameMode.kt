/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: GameMode.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

import androidx.annotation.StringRes
import com.alexis_benejan.townsio.R

// ☆ Description: Define los diferentes modos de juego y sus recursos de string asociados.

enum class GameMode(
    @StringRes val titleResId: Int,
    @StringRes val shortTitleResId: Int // Para nombres cortos en el leaderboard
) {
    FLAG_QUIZ(R.string.mode_flags, R.string.name_flags_short),
    SHIELD_QUIZ(R.string.mode_shields, R.string.name_shields_short),
    COGNOMENTO_QUIZ(R.string.mode_cognomentos, R.string.name_slogans_short)
}