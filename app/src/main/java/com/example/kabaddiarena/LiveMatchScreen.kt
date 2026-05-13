package com.example.kabaddiarena

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LiveMatchScreen(
    opponent: String,
    date: String,
    onFinish: (List<ActionType>) -> Unit
) {

    val actions = remember {
        mutableStateListOf<ActionType>()
    }

    // Total raids
    val totalRaids = actions.count {
        it == ActionType.EMPTY_RAID ||
                it == ActionType.TOUCH_POINT
    }

    // Successful actions
    val success = actions.count {
        it == ActionType.TOUCH_POINT ||
                it == ActionType.SUPER_TACKLE
    }

    // Success percentage
    val successRate =
        if (totalRaids > 0)
            (success * 100) / totalRaids
        else 0

    PremiumBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Header
            Text(
                text = "Kabaddi Arena",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Match info card
            PremiumCard {

                Text(
                    text = "Opponent: $opponent",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Date: $date",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Total Raids: $totalRaids",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Success Rate: $successRate%",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { successRate / 100f },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action buttons

            ActionButton("Touch Point") {
                actions.add(ActionType.TOUCH_POINT)
            }

            Spacer(modifier = Modifier.height(8.dp))

            ActionButton("Empty Raid") {
                actions.add(ActionType.EMPTY_RAID)
            }

            Spacer(modifier = Modifier.height(8.dp))

            ActionButton("Super Tackle") {
                actions.add(ActionType.SUPER_TACKLE)
            }

            Spacer(modifier = Modifier.height(8.dp))

            ActionButton("Bonus Point") {
                actions.add(ActionType.BONUS_POINT)
            }

            Spacer(modifier = Modifier.height(8.dp))

            ActionButton("Tackle") {
                actions.add(ActionType.TACKLE)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Undo button
            OutlinedButton(
                onClick = {

                    if (actions.isNotEmpty()) {
                        actions.removeLast()
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Undo Last Action",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Finish match button
            ActionButton("Finish Match") {

                onFinish(actions.toList())
            }
        }
    }
}