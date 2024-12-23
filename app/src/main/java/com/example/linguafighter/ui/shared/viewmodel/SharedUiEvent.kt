package com.example.linguafighter.ui.shared.viewmodel

import com.example.linguafighter.model.Question
import com.example.linguafighter.ui.screens.game.GameUiEvent

sealed class SharedUiEvent {
    data class UploadQuestionSet(val questionSet: List<Question>) : SharedUiEvent()
    data class UpdateAnswer(
        val option : String = "",
        val currentQuestion: Question,
        val questionNumber : Int
    ) : SharedUiEvent()
    object FetchQuestions : SharedUiEvent()
    object DiscardAnswers : SharedUiEvent()
}