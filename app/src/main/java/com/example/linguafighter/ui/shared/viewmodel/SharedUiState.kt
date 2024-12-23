package com.example.linguafighter.ui.shared.viewmodel

import com.example.linguafighter.model.Question
import com.example.linguafighter.model.Answer

data class SharedUiState(
    val questionSet : List<Question> = listOf(),
    val answerSet : List<Answer> = listOf()
)
