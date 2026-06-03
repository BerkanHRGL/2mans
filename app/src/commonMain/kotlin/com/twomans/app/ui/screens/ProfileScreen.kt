package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ── PHOTO CARD WITH OVERLAPPING BADGES ────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Inner box gives top/bottom room for badges to overflow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.92f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(ForestGreen)
                ) {
                    // Pair silhouette circles
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(170.dp)
                            .height(90.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .align(Alignment.CenterStart)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f))
                        )
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .align(Alignment.CenterEnd)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f))
                        )
                    }
                }
            }

            // Prompt badge — overlaps top-left of card
            Box {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 3.dp, y = 3.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFFAA8C28))
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Gold)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "prompt 04 · \"saturday\"",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                }
            }

            // Amsterdam badge — overlaps bottom-right of card
            Box(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 3.dp, y = 3.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFFCC9A88))
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(Blush)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Amsterdam",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color(0xFF5A4A42)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── NAMES ─────────────────────────────────────────────────────
        Text(
            text = "Ava & Mira",
            fontFamily = FrancoisOneFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 40.sp,
            color = Color(0xFF1A1A1A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── SUBTITLE ──────────────────────────────────────────────────
        Text(
            text = "27 & 28 · besties since forever",
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Taupe,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── STATS ─────────────────────────────────────────────────────
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatBadge("12 matches",     Gold,               Color(0xFFAA8C28))
                StatBadge("3 double dates", Blush,              Color(0xFFCC9A88))
            }
            Spacer(modifier = Modifier.height(10.dp))
            StatBadge("04 / 17 prompts", Color(0xFFE0DDD4), Color(0xFFBBB5AC))
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun StatBadge(text: String, bgColor: Color, shadowColor: Color) {
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 3.dp, y = 3.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(shadowColor)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(bgColor)
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF1A1A1A)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    _2mansTheme { ProfileScreen() }
}
