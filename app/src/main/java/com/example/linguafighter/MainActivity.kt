package com.example.linguafighter

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.linguafighter.ui.LinguafighterApp
import com.example.linguafighter.ui.theme.linguafighterTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            linguafighterTheme{
                LinguafighterApp()
            }
        }

    }
}