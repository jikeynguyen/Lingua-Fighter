package com.example.linguafighter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.linguafighter.model.Question

data class QuizOption(
    @DrawableRes val image : Int,
    @StringRes val language: Int,
    val dataset : List<Question>
)
