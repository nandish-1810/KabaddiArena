package com.example.kabaddiarena

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    actions: List<ActionType>,
    touchPoints: Int,
    emptyRaids: Int,
    superTackles: Int,
    bonusPoints: Int,
    tackles: Int,
    onRestart: () -> Unit,
    onWatchVideo: () -> Unit
) {

    val touchPoints = actions.count {
        it == ActionType.TOUCH_POINT
    }

    val emptyRaids = actions.count {
        it == ActionType.EMPTY_RAID
    }

    val superTackles = actions.count {
        it == ActionType.SUPER_TACKLE
    }

    val bonusPoints = actions.count {
        it == ActionType.BONUS_POINT
    }

    val tackles = actions.count {
        it == ActionType.TACKLE
    }

    val totalRaids =
        touchPoints + emptyRaids

    val successfulRaids =
        touchPoints + superTackles

    val successRate =
        if (totalRaids > 0)
            (successfulRaids * 100) / totalRaids
        else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF021B2B),
                        Color(0xFF12394A)
                    )
                )
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Match Summary",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // Circular Progress
        Box(
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                progress = { successRate / 100f },
                strokeWidth = 10.dp,
                color = Color(0xFFFFB300),
                trackColor = Color.DarkGray,
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = "$successRate%",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            SmallStatCard(
                title = "Raids",
                value = totalRaids.toString()
            )

            SmallStatCard(
                title = "Success",
                value = successfulRaids.toString()
            )
        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        // Breakdown Card
        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(24.dp),

            colors = cardColors(
                containerColor =
                    Color.White.copy(alpha = 0.12f)
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Performance Breakdown",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                BreakdownItem(
                    title = "Touch Points",
                    value = touchPoints,
                    progress = touchPoints / 10f,
                    color = Color.Red
                )

                BreakdownItem(
                    title = "Empty Raids",
                    value = emptyRaids,
                    progress = emptyRaids / 10f,
                    color = Color.LightGray
                )

                BreakdownItem(
                    title = "Super Tackles",
                    value = superTackles,
                    progress = superTackles / 10f,
                    color = Color.Magenta
                )

                BreakdownItem(
                    title = "Bonus Points",
                    value = bonusPoints,
                    progress = bonusPoints / 10f,
                    color = Color.Cyan
                )

                BreakdownItem(
                    title = "Tackles",
                    value = tackles,
                    progress = tackles / 10f,
                    color = Color.Green
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Share Card
        // Share Performance Card
        PerformanceCard(
            successRate = successRate,
            totalRaids = totalRaids,
            successfulRaids = successfulRaids,
            touchPoints = touchPoints,
            emptyRaids = emptyRaids,
            superTackles = superTackles,
            bonusPoints = bonusPoints,
            tackles = tackles
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = onWatchVideo,

            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF7A00)
            )
        ) {

            Text(
                text = "Watch Pro Tips 🎥",
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onRestart,

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Start New Match"
            )
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}

@Composable
fun SmallStatCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier.width(140.dp),

        shape = RoundedCornerShape(20.dp),

        colors = cardColors(
            containerColor =
                Color.White.copy(alpha = 0.12f)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                color = Color.LightGray
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = value,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun BreakdownItem(
    title: String,
    value: Int,
    progress: Float,
    color: Color
) {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text = title,
                color = Color.White
            )

            Text(
                text = value.toString(),
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        LinearProgressIndicator(
            progress = {
                progress.coerceIn(0f, 1f)
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(50)),

            color = color,

            trackColor =
                Color.DarkGray
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )
    }
}