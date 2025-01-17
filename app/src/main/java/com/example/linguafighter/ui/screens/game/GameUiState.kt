package com.example.linguafighter.ui.screens.game

import com.example.linguafighter.model.Question

data class GameUiState(
    val currentQuestion : Question = Question(),
    val questionNumber : Int = 0,
    val time : Long = 0,
    val currentScore : Int = 0,
    val answeredCorrectly: Int = 0,
    val timerEnd : Boolean = false,
    val canClick : Boolean = false,
    val quizEnd : Boolean = false,
    val selected : String = ""
)
