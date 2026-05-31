package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

@Composable
fun OnboardingScreen(onContinue: () -> Unit = {}) {
    val clipboardManager = LocalClipboardManager.current
    val pairLink = "2mans.app/____"
    var linkCopied by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

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
                text = "2mans only works in pairs. Send your friend your link and your friend will join you.",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = ForestGreen.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── PAIR LINK CARD ─────────────────────────────────────────
            FlatCard {
                Text(
                    text = "your pair link",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.55f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = pairLink,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 26.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Share button (gold pill)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(26.dp))
                            .background(Gold)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { /* share intent */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Text Mira",
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }

                    // Copy button (circle)
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(ForestGreenMid)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                clipboardManager.setText(AnnotatedString(pairLink))
                                linkCopied = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ContentCopy,
                            contentDescription = "Copy link",
                            tint = if (linkCopied) Gold else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // ── STICKY BOTTOM BUTTON ───────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 24.dp, end = 24.dp, bottom = 28.dp)
        ) {
            // Flat shadow
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 4.dp, y = 4.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(ForestGreenDeep)
            )
            // Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(ForestGreen)
                    .clickable(onClick = onContinue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "I sent it →",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}

// Reusable card with flat offset shadow — will be used on other screens too
@Composable
fun FlatCard(content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        // Shadow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(ForestGreenDeep)
        )
        // Card layer
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
    _2mansTheme {
        OnboardingScreen()
    }
}
