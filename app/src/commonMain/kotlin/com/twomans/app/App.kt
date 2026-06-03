package com.twomans.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.twomans.app.ui.screens.*
import com.twomans.app.ui.theme.Cream
import com.twomans.app.ui.theme.ForestGreen
import com.twomans.app.ui.theme._2mansTheme
import com.twomans.app.ui.viewmodel.AppViewModel

private val TAB_ROUTES = setOf("swipe", "chat", "profile")

@Composable
fun App() {
    _2mansTheme {
        val vm: AppViewModel = viewModel()
        val authState by vm.authState.collectAsState()
        val currentPair by vm.currentPair.collectAsState()

        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        // Initial routing only: send the user to the right screen once when
        // auth first resolves, and back to onboarding on sign-out. Forward
        // navigation after that is driven by explicit button taps, so flipping
        // hasPair (e.g. creating a pair) does NOT yank the user off a screen.
        var didInitialRoute by remember { mutableStateOf(false) }
        LaunchedEffect(authState) {
            when (val state = authState) {
                is AppViewModel.AuthState.Unauthenticated -> {
                    navController.navigate("onboarding") { popUpTo(0) { inclusive = true } }
                    didInitialRoute = false
                }
                is AppViewModel.AuthState.Authenticated -> {
                    if (!didInitialRoute) {
                        didInitialRoute = true
                        val dest = if (state.hasPair) "swipe" else "pair_setup"
                        navController.navigate(dest) { popUpTo(0) { inclusive = true } }
                    }
                }
                else -> {}
            }
        }

        Scaffold(
            bottomBar = {
                if (currentRoute in TAB_ROUTES) {
                    BottomNavBar(
                        currentRoute = currentRoute ?: "swipe",
                        onNavigate = { route ->
                            navController.navigate(route) { launchSingleTop = true }
                        }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "loading",
                modifier = Modifier.padding(padding)
            ) {
                composable("loading") {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Cream),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator(color = ForestGreen) }
                }
                composable("onboarding") {
                    OnboardingScreen(
                        onGetStarted = { navController.navigate("signup") },
                        onSignIn = { navController.navigate("signin") }
                    )
                }
                composable("signup") {
                    SignUpScreen(
                        isLoading = vm.isLoading,
                        error = vm.error,
                        onSignUp = { name, email, pass -> vm.signUp(name, email, pass) {} },
                        onSignIn = { navController.navigate("signin") }
                    )
                }
                composable("signin") {
                    SignInScreen(
                        isLoading = vm.isLoading,
                        error = vm.error,
                        onSignIn = { email, pass -> vm.signIn(email, pass) {} },
                        onSignUp = { navController.navigate("signup") }
                    )
                }
                composable("pair_setup") {
                    PairSetupScreen(
                        currentPair = currentPair,
                        isLoading = vm.isLoading,
                        error = vm.error,
                        onCreatePair = { vm.createPair() },
                        onJoinPair = { code ->
                            vm.joinPair(code) {
                                navController.navigate("swipe") { popUpTo(0) { inclusive = true } }
                            }
                        },
                        onContinue = {
                            navController.navigate("swipe") { popUpTo(0) { inclusive = true } }
                        }
                    )
                }
                composable("swipe") {
                    SwipeScreen(onLike = { navController.navigate("match") })
                }
                composable("match") {
                    MatchScreen(
                        onBack = { navController.popBackStack() },
                        onSayHi = { navController.navigate("chat") },
                        onKeepSwiping = { navController.popBackStack() }
                    )
                }
                composable("chat") {
                    val hasBack = navController.previousBackStackEntry != null
                    ChatScreen(onBack = if (hasBack) { { navController.popBackStack() } } else null)
                }
                composable("profile") {
                    ProfileScreen()
                }
            }
        }
    }
}
