package com.example.linguafighter.model

data class Question (
    val word: String = "",
    val options : List<String> = listOf(),
    val answer : String = ""
)