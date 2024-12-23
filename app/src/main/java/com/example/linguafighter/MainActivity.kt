package com.example.linguafighter

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.linguafighter.ui.theme.linguafighterTheme
import com.example.linguafighter.ui.linguafighterApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            linguafighterTheme{
                linguafighterApp()
            }
        }

    }
}