/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: AboutActivity.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexis_benejan.townsio.databinding.ActivityAboutBinding
import android.content.Intent
import android.net.Uri
import android.widget.Toast

// ☆ Description: Muestra información sobre el desarrollador, la versión y enlaces externos.

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la barra de acción para mostrar el botón de retroceso
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_title)

        // 1. Configurar Listener para Ko-fi
        binding.btnKofi.setOnClickListener {
            openWebPage(getString(R.string.url_kofi))
        }

        // 2. Configurar Listener para GitHub
        binding.btnGithub.setOnClickListener {
            openWebPage(getString(R.string.url_github))
        }
    }

    // ☆ Description: Abre una URL en el navegador del dispositivo.
    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)

        // Asegurarse de que hay una aplicación (navegador) para manejar el Intent
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.error_no_browser), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}