package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

@Composable
fun SignUpScreen(
    isLoading: Boolean = false,
    error: String? = null,
    onSignUp: (name: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onSignIn: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
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
                text = "Create your\naccount.",
                fontFamily = FrancoisOneFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 44.sp,
                lineHeight = 50.sp,
                color = ForestGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "You'll invite your plus-one after sign-up.",
                fontFamily = FontFamily.Default,
                fontSize = 15.sp,
                color = Taupe
            )

            Spacer(modifier = Modifier.height(36.dp))

            AppTextField(value = name, onValueChange = { name = it }, label = "Your name")
            Spacer(modifier = Modifier.height(14.dp))
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

            // CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (isLoading) ForestGreenMid else ForestGreen)
                    .clickable(enabled = !isLoading) {
                        if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                            onSignUp(name.trim(), email.trim(), password)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isLoading) "Creating account…" else "Continue →",
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
                Text("Already have an account? ", fontSize = 14.sp, color = Taupe)
                Text(
                    text = "Sign in",
                    fontSize = 14.sp,
                    color = ForestGreen,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onSignIn
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Taupe,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFF1A1A1A),
                fontSize = 16.sp,
                fontFamily = FontFamily.Default
            ),
            cursorBrush = SolidColor(ForestGreen),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            decorationBox = { inner ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(WarmWhite)
                        .border(1.dp, LightTaupe, RoundedCornerShape(14.dp))
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    inner()
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
    _2mansTheme { SignUpScreen() }
}
