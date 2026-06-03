package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

@Composable
fun SignInScreen(
    isLoading: Boolean = false,
    error: String? = null,
    onSignIn: (email: String, password: String) -> Unit = { _, _ -> },
    onSignUp: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = "Welcome\nback.",
                fontFamily = FrancoisOneFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 44.sp,
                lineHeight = 50.sp,
                color = ForestGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to continue with your pair.",
                fontFamily = FontFamily.Default,
                fontSize = 15.sp,
                color = Taupe
            )

            Spacer(modifier = Modifier.height(36.dp))

            AppTextField(
                value = email, onValueChange = { email = it },
                label = "Email", keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(14.dp))
            AppTextField(
                value = password, onValueChange = { password = it },
                label = "Password", isPassword = true
            )

            if (error != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = error, color = Color(0xFFB33A3A), fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (isLoading) ForestGreenMid else ForestGreen)
                    .clickable(enabled = !isLoading) {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            onSignIn(email.trim(), password)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isLoading) "Signing in…" else "Sign in →",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don't have an account? ", fontSize = 14.sp, color = Taupe)
                Text(
                    text = "Sign up",
                    fontSize = 14.sp,
                    color = ForestGreen,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onSignUp
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    _2mansTheme { SignInScreen() }
}
