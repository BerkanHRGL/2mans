package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

@Composable
fun MatchScreen(
    onSayHi: () -> Unit = {},
    onKeepSwiping: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MatchYellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // ── HEADLINE ───────────────────────────────────────────────
            Text(
                text = "It's a\nmatch!",
                textAlign = TextAlign.Center,
                fontFamily = FrancoisOneFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 54.sp,
                lineHeight = 58.sp,
                color = ForestGreen
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── SUBTITLE ───────────────────────────────────────────────
            Text(
                text = "You & Mira matched with\nHenk & Jan.",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = ForestGreen.copy(alpha = 0.65f)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // ── OVERLAPPING POLAROID CARDS ─────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentAlignment = Alignment.Center
            ) {
                // Left card — behind, rotated CCW
                PolaroidCard(
                    name = "Ava & Mira",
                    modifier = Modifier
                        .offset(x = (-62).dp, y = 0.dp)
                        .rotate(-7f)
                )
                // Right card — in front, rotated CW
                PolaroidCard(
                    name = "Henk & Jan",
                    modifier = Modifier
                        .offset(x = 62.dp, y = 24.dp)
                        .rotate(4f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // ── BOTTOM ACTIONS ─────────────────────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // say hi button with flat shadow
            Box(modifier = Modifier.fillMaxWidth()) {
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
                        .background(ForestGreen)
                        .clickable(onClick = onSayHi),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "say hi →",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "or keep swiping",
                color = ForestGreen,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onKeepSwiping
                )
            )
        }
    }
}

@Composable
private fun PolaroidCard(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(185.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(WarmWhite)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(ForestGreen)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color(0xFF1A1A1A)
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MatchScreenPreview() {
    _2mansTheme {
        MatchScreen()
    }
}
