package com.twomans.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twomans.app.ui.screens.MatchScreen
import com.twomans.app.ui.screens.OnboardingScreen
import com.twomans.app.ui.screens.SwipeScreen
import com.twomans.app.ui.theme._2mansTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2mansTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") {
                        OnboardingScreen(onContinue = { navController.navigate("swipe") })
                    }
                    composable("swipe") {
                        SwipeScreen(onLike = { navController.navigate("match") })
                    }
                    composable("match") {
                        MatchScreen(
                            onSayHi = { /* chat screen — coming soon */ },
                            onKeepSwiping = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
