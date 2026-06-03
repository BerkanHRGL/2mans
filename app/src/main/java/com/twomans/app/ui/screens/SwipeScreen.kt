package com.twomans.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twomans.app.ui.theme.*

@Composable
fun SwipeScreen(
    onPass: () -> Unit = {},
    onLike: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // ── PROMPT BADGE ───────────────────────────────────────────────
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(Gold)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("\"Show us your favorite ")
                    withStyle(SpanStyle(color = ForestGreen)) { append("Saturday") }
                    append(".\"")
                },
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── PHOTO CARD ─────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(ForestGreen)
            )

            // Distance badge — top-right overlay
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 14.dp, end = 14.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Blush)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "3 km away",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF5A4A42)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── NAMES ──────────────────────────────────────────────────────
        Text(
            text = "Henk & Jan",
            fontFamily = FrancoisOneFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 34.sp,
            color = Color(0xFF1A1A1A)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ── SUBTITLE ───────────────────────────────────────────────────
        Text(
            text = "both 26 · Amsterdam",
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color(0xFF8A8070)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── ACTION BUTTONS ─────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pass (X)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color(0xFF1A1A1A), CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onPass
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Pass",
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            // Like (heart)
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(ForestGreen)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onLike
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Like",
                    tint = Gold,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SwipeScreenPreview() {
    _2mansTheme {
        SwipeScreen()
    }
}
