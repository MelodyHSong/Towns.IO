/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: TownData.kt
☆ Date: 2025-12-13
☆
*/

package com.alexis_benejan.townsio

// ☆ Description: Data class para un municipio de Puerto Rico.

data class Town(
    val id: Int,            // ID único.
    val name: String,       // Nombre del municipio.
    val region: String,     // Región (Norte, Sur, Este, Oeste, Central).
    val cognomento: String, // Frase o expresión descriptiva.
    val flagResource: Int,  // ID del recurso de la bandera.
    val shieldResource: Int // ID del recurso del escudo.
)