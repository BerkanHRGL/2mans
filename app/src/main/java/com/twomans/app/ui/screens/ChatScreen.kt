package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

private data class MockMessage(
    val sender: String,
    val isOwn: Boolean,
    val avatarColor: Color,
    val narrowBy: Dp
)

private val headerAvatarColors = listOf(
    Color(0xFFAA9068),
    Color(0xFF9A8060),
    Color(0xFF8A9870),
    Color(0xFF7A8E60),
)

private val mockMessages = listOf(
    MockMessage("Henk", false, Color(0xFFAA9068), 20.dp),
    MockMessage("Jan",  false, Color(0xFF8A9870), 44.dp),
    MockMessage("Mira", true,  Color(0xFF9A8878), 12.dp),
    MockMessage("Ava",  true,  Color(0xFF7A8E60), 58.dp),
)

@Composable
fun ChatScreen(onBack: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // ── HEADER ─────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button (when navigated from another screen)
                if (onBack != null) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(WarmWhite)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onBack
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = ForestGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }

                // 4 overlapping avatar circles with cream ring separator
                Box(modifier = Modifier.width(128.dp).height(44.dp)) {
                    headerAvatarColors.forEachIndexed { i, color ->
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .offset(x = (i * 28).dp)
                                .clip(CircleShape)
                                .background(Cream)
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "the four of us",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color(0xFF1A1A1A)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "matched 12 min ago ",
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = Taupe
                        )
                        Text(text = "✦", fontSize = 12.sp, color = Gold)
                    }
                }
            }

            // ── MESSAGES ───────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                mockMessages.forEach { MessageRow(it) }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // ── INPUT BAR ──────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Cream)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(WarmWhite)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "type a thing...",
                    fontSize = 15.sp,
                    color = Taupe.copy(alpha = 0.7f)
                )
            }
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(ForestGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send",
                    tint = Gold,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
private fun MessageRow(message: MockMessage) {
    val isOwn      = message.isOwn
    val bubbleBg   = if (isOwn) ForestGreen else MatchYellow
    val shadowBg   = if (isOwn) ForestGreenDeep else Color(0xFFBF9A28)

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        if (!isOwn) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Cream)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(message.avatarColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    end   = if (!isOwn) message.narrowBy else 0.dp,
                    start = if (isOwn)  message.narrowBy else 0.dp
                ),
            horizontalAlignment = if (isOwn) Alignment.End else Alignment.Start
        ) {
            Text(text = message.sender, fontSize = 12.sp, color = Taupe)
            Spacer(modifier = Modifier.height(4.dp))
            Box {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 3.dp, y = 3.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(shadowBg)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(bubbleBg)
                )
            }
        }

        if (isOwn) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Cream)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(message.avatarColor)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatScreenPreview() {
    _2mansTheme { ChatScreen() }
}
