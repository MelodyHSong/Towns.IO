/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: QuizActivity.kt
☆ Date: 2025-12-14
☆
*/

package com.alexis_benejan.townsio

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.media.MediaPlayer
import android.graphics.Color
import android.widget.Toast
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

// ☆ Description: Lógica principal del quiz, manejo de preguntas, respuestas, feedback multimedia y captura de nombre.

class QuizActivity : AppCompatActivity() {

    // --- Variables de Estado ---
    private lateinit var currentMode: GameMode
    private lateinit var currentDifficulty: Difficulty
    private var totalQuestions = 0
    private var questionCounter = 0
    private var correctAnswers = 0
    private var totalGuesses = 0
    private lateinit var allTowns: List<Town>
    private lateinit var availableTowns: MutableList<Town>
    private lateinit var currentCorrectTown: Town

    // --- Variables de Tiempo ---
    private var startTime: Long = 0
    private var finishTime: Long = 0

    // --- Elementos de UI ---
    private lateinit var tvQuestionNumber: TextView
    private lateinit var ivImageQuestion: ImageView
    private lateinit var tvCognomentoQuestion: TextView
    private lateinit var btnOption1: Button
    private lateinit var btnOption2: Button
    private lateinit var btnOption3: Button
    private lateinit var btnOption4: Button
    private val optionButtons by lazy { listOf(btnOption1, btnOption2, btnOption3, btnOption4) }

    // --- Multimedia ---
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Inicializa elementos de UI
        tvQuestionNumber = findViewById(R.id.tv_question_number)
        ivImageQuestion = findViewById(R.id.iv_question_image)
        tvCognomentoQuestion = findViewById(R.id.tv_question_cognomento)
        btnOption1 = findViewById(R.id.btn_option_1)
        btnOption2 = findViewById(R.id.btn_option_2)
        btnOption3 = findViewById(R.id.btn_option_3)
        btnOption4 = findViewById(R.id.btn_option_4)

        // 1. Obtener Dificultad y Modo de Juego
        val modeName = intent.getStringExtra("GAME_MODE") ?: GameMode.FLAG_QUIZ.name
        currentMode = try {
            GameMode.valueOf(modeName)
        } catch (e: IllegalArgumentException) {
            GameMode.FLAG_QUIZ
        }

        val difficultyName = intent.getStringExtra("DIFFICULTY_MODE") ?: Difficulty.EASY.name
        currentDifficulty = Difficulty.valueOf(difficultyName)

        // 2. Establecer el número total de preguntas
        totalQuestions = currentDifficulty.maxQuestions

        // 3. Inicializar listas de municipios usando TownDataSource
        allTowns = TownDataSource.getTowns()
        availableTowns = allTowns.toMutableList()

        // 4. INICIAR EL CONTADOR DE TIEMPO
        startTime = System.currentTimeMillis()

        for (button in optionButtons) {
            button.setOnClickListener { view ->
                handleAnswer((view as Button).text.toString())
            }
        }

        loadNewQuestion()
    }

    // ☆ Description: Selecciona un nuevo municipio, removiéndolo de la lista disponible para evitar repetición.
    private fun loadNewQuestion() {
        if (questionCounter >= totalQuestions || availableTowns.isEmpty()) {
            endQuiz()
            return
        }

        questionCounter++
        tvQuestionNumber.text = getString(R.string.question_format, questionCounter, totalQuestions)

        val randomIndex = Random.nextInt(availableTowns.size)
        currentCorrectTown = availableTowns[randomIndex]
        availableTowns.removeAt(randomIndex)

        // Restablece la apariencia del botón
        val defaultColor = Color.GRAY
        optionButtons.forEach {
            it.isEnabled = true
            it.setBackgroundColor(defaultColor)
        }

        // Lógica de visualización de la pregunta según el modo de juego
        when (currentMode) {
            GameMode.FLAG_QUIZ -> {
                ivImageQuestion.setImageResource(currentCorrectTown.flagResource)
                ivImageQuestion.visibility = View.VISIBLE
                tvCognomentoQuestion.visibility = View.GONE
            }
            GameMode.SHIELD_QUIZ -> {
                ivImageQuestion.setImageResource(currentCorrectTown.shieldResource)
                ivImageQuestion.visibility = View.VISIBLE
                tvCognomentoQuestion.visibility = View.GONE
            }
            GameMode.COGNOMENTO_QUIZ -> {
                tvCognomentoQuestion.text = currentCorrectTown.cognomento
                tvCognomentoQuestion.visibility = View.VISIBLE
                ivImageQuestion.visibility = View.GONE
            }
        }

        // Genera 3 opciones incorrectas y aleatoriza
        val incorrectOptions = allTowns
            .filter { it.name != currentCorrectTown.name }
            .shuffled()
            .take(3)
            .map { it.name }

        val allOptions = (incorrectOptions + currentCorrectTown.name).shuffled()

        optionButtons.forEachIndexed { index, button ->
            button.text = allOptions[index]
            button.isEnabled = true
        }

        // LLAMADA A ANIMACIÓN DE ENTRADA
        animate(false)
    }

    // ☆ Description: Procesa la respuesta seleccionada, da feedback visual y sonoro, y avanza a la siguiente pregunta después de un retraso.
    private fun handleAnswer(selectedOption: String) {
        // Desactivar todos los botones inmediatamente para evitar múltiples clics
        optionButtons.forEach { it.isEnabled = false }
        totalGuesses++

        val isCorrect = selectedOption == currentCorrectTown.name
        val selectedButton = findButtonByText(selectedOption)

        mediaPlayer?.release()
        mediaPlayer = null

        if (isCorrect) {
            correctAnswers++
            // Feedback de sonido: correct.wav
            mediaPlayer = MediaPlayer.create(this, R.raw.correct)
            mediaPlayer?.start()

            // Feedback visual: botón verde
            selectedButton?.setBackgroundColor(Color.GREEN)
            Toast.makeText(this, getString(R.string.correct_answer), Toast.LENGTH_SHORT).show()

            // Retraso para ver el feedback y avanzar
            handler.postDelayed({
                animate(true)
            }, 1500)

        } else {
            // El intento cuenta como fallo, debe avanzar.

            // Feedback de sonido: error.wav
            mediaPlayer = MediaPlayer.create(this, R.raw.error)
            mediaPlayer?.start()

            // Feedback visual: botón incorrecto rojo, respuesta correcta verde
            selectedButton?.setBackgroundColor(Color.RED)
            findButtonByText(currentCorrectTown.name)?.setBackgroundColor(Color.GREEN)
            Toast.makeText(this, getString(R.string.incorrect_answer) + ": " + currentCorrectTown.name, Toast.LENGTH_LONG).show()

            // Retraso para ver el feedback de error y luego AVANZAR
            handler.postDelayed({
                animate(true)
            }, 1500)
        }
    }

    // ☆ Description: Implementa una animación circular (reveal/hide) para la transición de imágenes, o avanza sin animación si el modo es COGNOMENTO.
    private fun animate(animateOut: Boolean) {
        // La animación no se ejecuta para la primera pregunta al inicio
        if (questionCounter <= 1 && !animateOut) return

        // Solo animamos la imagen si el modo NO es COGNOMENTO
        if (currentMode == GameMode.FLAG_QUIZ || currentMode == GameMode.SHIELD_QUIZ) {

            // Calculamos el centro y el radio máximo para la animación circular
            val centerX = ivImageQuestion.left + ivImageQuestion.width / 2
            val centerY = ivImageQuestion.top + ivImageQuestion.height / 2
            val radius = kotlin.math.max(ivImageQuestion.width, ivImageQuestion.height)

            val animator: Animator

            if (animateOut) {
                // ANMACIÓN DE SALIDA (Ocultar)
                animator = ViewAnimationUtils.createCircularReveal(
                    ivImageQuestion, centerX, centerY, radius.toFloat(), 0f
                )
                // Al finalizar la animación de salida, carga la nueva pregunta
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        loadNewQuestion()
                    }
                })
            } else {
                // ANMACIÓN DE ENTRADA (Revelar)
                animator = ViewAnimationUtils.createCircularReveal(
                    ivImageQuestion, centerX, centerY, 0f, radius.toFloat()
                )
            }

            animator.duration = 750 // Duración de 750 ms
            animator.start()
        } else {
            // Si es COGNOMENTO, simplemente llama a loadNewQuestion sin animación
            if (animateOut) {
                loadNewQuestion()
            }
        }
    }

    // ☆ Description: Finaliza el quiz, calcula el tiempo total y solicita el nombre del usuario para guardar el récord.
    private fun endQuiz() {
        // 1. DETENER Y CALCULAR EL TIEMPO
        finishTime = System.currentTimeMillis() - startTime

        // 2. CREAR CAMPO DE TEXTO PARA EL DIÁLOGO
        val input = EditText(this)
        input.hint = getString(R.string.enter_your_name_hint)

        // 3. MOSTRAR DIÁLOGO para capturar nombre
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.new_high_score_title))
            .setView(input)
            .setPositiveButton(getString(R.string.action_save)) { dialog, which ->
                var userName = input.text.toString().trim()
                if (userName.isEmpty()) {
                    userName = getString(R.string.default_player_name)
                }
                // 4. Llamar a la función de guardado
                saveAndLaunchHighScores(userName, finishTime)
            }
            .setNegativeButton(getString(R.string.action_cancel)) { dialog, which ->
                // Si el usuario cancela, guarda con nombre por defecto
                saveAndLaunchHighScores(getString(R.string.default_player_name), finishTime)
            }
            .setCancelable(false) // Forzar al usuario a elegir una opción
            .show()
    }

    // ☆ Description: Crea la entrada de puntaje, la guarda en HighScoresManager e inicia HighScoresActivity.
    private fun saveAndLaunchHighScores(userName: String, timeTaken: Long) {
        val newEntry = HighScoreEntry(
            userName = userName,
            score = correctAnswers,
            guesses = totalGuesses,
            difficulty = currentDifficulty.name,
            mode = currentMode.name,
            timeMillis = timeTaken,
            timestamp = System.currentTimeMillis()
        )

        val manager = HighScoresManager(this)
        val newRecordSet = manager.saveNewScore(newEntry)

        val intent = Intent(this, HighScoresActivity::class.java).apply {
            putExtra("SCORE_CORRECT", correctAnswers)
            putExtra("SCORE_TOTAL_GUESSES", totalGuesses)
            putExtra("NEW_RECORD", newRecordSet)
            putExtra("DIFFICULTY_MODE", currentDifficulty.name)
            putExtra("GAME_MODE", currentMode.name)
        }
        startActivity(intent)
        finish()
    }

    private fun findButtonByText(text: String): Button? {
        return optionButtons.find { it.text.toString() == text }
    }

    // ☆ Description: Libera el reproductor de audio al destruir la actividad.
    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}