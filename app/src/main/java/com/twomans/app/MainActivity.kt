package com.twomans.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.twomans.app.ui.screens.OnboardingScreen
import com.twomans.app.ui.theme._2mansTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2mansTheme {
                OnboardingScreen()
            }
        }
    }
}
