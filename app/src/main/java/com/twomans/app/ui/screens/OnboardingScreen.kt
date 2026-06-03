package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit = {},
    onSignIn: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            // ── HEADLINE ───────────────────────────────────────────────
            Text(
                text = "Grab your\nperson.",
                fontFamily = FrancoisOneFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 52.sp,
                lineHeight = 56.sp,
                color = ForestGreen
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── BODY ───────────────────────────────────────────────────
            Text(
                text = "2mans works in pairs. Create an account, invite your plus-one, and start matching together.",
                style = MaterialTheme.typography.bodyLarge,
                color = ForestGreen.copy(alpha = 0.7f)
            )
        }

        // ── BOTTOM ACTIONS ─────────────────────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 28.dp, end = 28.dp, bottom = 32.dp)
        ) {
            // Get started button with flat shadow
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 4.dp, y = 4.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(ForestGreenDeep)
                )
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text(
                        text = "Get started →",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

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
        }
    }
}

// Reusable flat-shadow card — used on pair setup and other screens
@Composable
fun FlatCard(content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(ForestGreenDeep)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(ForestGreen)
                .padding(20.dp),
            content = content
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
    _2mansTheme { OnboardingScreen() }
}
