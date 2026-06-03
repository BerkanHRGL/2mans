package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Link
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.data.model.AppPair
import com.twomans.app.ui.theme.*

@Composable
fun PairSetupScreen(
    currentPair: AppPair? = null,
    isLoading: Boolean = false,
    error: String? = null,
    onCreatePair: () -> Unit = {},
    onJoinPair: (code: String) -> Unit = {},
    onContinue: () -> Unit = {}
) {
    val clipboard = LocalClipboardManager.current
    var isCreateMode by remember { mutableStateOf(true) }
    var joinCode by remember { mutableStateOf("") }
    var linkCopied by remember { mutableStateOf(false) }

    // Auto-create pair when switching to Create tab
    LaunchedEffect(isCreateMode) {
        if (isCreateMode && currentPair == null) onCreatePair()
    }

    val inviteLink = currentPair?.inviteCode?.let { "2mans.app/join/$it" } ?: "generating…"

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
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Grab your\nperson.",
                fontFamily = FrancoisOneFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 48.sp,
                lineHeight = 54.sp,
                color = ForestGreen
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Share your link with a friend, or enter their code to join their pair.",
                fontFamily = FontFamily.Default,
                fontSize = 15.sp,
                lineHeight = 23.sp,
                color = Taupe
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── MODE TOGGLE ────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .background(WarmWhite)
                    .padding(4.dp)
            ) {
                listOf(true to "Create a pair", false to "Join a pair").forEach { (isCreate, label) ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(50.dp))
                            .background(if (isCreateMode == isCreate) ForestGreen else Color.Transparent)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { isCreateMode = isCreate }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = if (isCreateMode == isCreate) Color.White else Taupe
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isCreateMode) {
                // ── PAIR LINK CARD ─────────────────────────────────────
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
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "your pair link",
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = inviteLink,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            fontSize = 22.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Link,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text("Share link", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(ForestGreenMid)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        if (currentPair != null) {
                                            clipboard.setText(AnnotatedString(inviteLink))
                                            linkCopied = true
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ContentCopy,
                                    contentDescription = "Copy",
                                    tint = if (linkCopied) Gold else Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                // ── JOIN MODE ──────────────────────────────────────────
                AppTextField(
                    value = joinCode,
                    onValueChange = { joinCode = it.uppercase() },
                    label = "Invite code",
                    keyboardType = KeyboardType.Text
                )
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = error, color = Color(0xFFB33A3A), fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // ── BOTTOM BUTTON ──────────────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 4.dp, y = 4.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(ForestGreenDeep)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (isLoading) ForestGreenMid else ForestGreen)
                    .clickable(enabled = !isLoading) {
                        if (isCreateMode) {
                            if (currentPair != null) onContinue()
                        } else {
                            if (joinCode.isNotBlank()) onJoinPair(joinCode)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when {
                        isLoading -> "Please wait…"
                        isCreateMode -> "I sent it →"
                        else -> "Join pair →"
                    },
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PairSetupScreenPreview() {
    _2mansTheme { PairSetupScreen() }
}
