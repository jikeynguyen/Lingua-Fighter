package com.example.linguafighter.model

import android.icu.lang.UProperty.NameChoice
import com.example.linguafighter.model.Question

data class Answer(
    val question: Question,
    val choice: String,
    val questionNumber : Int
)
