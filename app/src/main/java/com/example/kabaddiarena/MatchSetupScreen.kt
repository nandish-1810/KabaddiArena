package com.example.kabaddiarena

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MatchSetupScreen(onStart: (String, String) -> Unit) {

    var opponent by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(
                Brush.Companion.verticalGradient(
                    listOf(Color(0xFF0F3D2E), Color(0xFF1F6F50))
                )
            ),
        contentAlignment = Alignment.Companion.Center   // ✅ CENTER EVERYTHING
    ) {

        Card(
            modifier = Modifier.Companion
                .padding(20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {

            Column(
                modifier = Modifier.Companion
                    .padding(20.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally   // ✅ CENTER CONTENT
            ) {

                Text(
                    "Kabaddi Arena",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Companion.Black
                )

                Spacer(modifier = Modifier.Companion.height(20.dp))

                OutlinedTextField(
                    value = opponent,
                    onValueChange = { opponent = it },
                    label = { Text("Opponent Team") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    modifier = Modifier.Companion.fillMaxWidth()
                )

                Spacer(modifier = Modifier.Companion.height(12.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Match Date") },
                    leadingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    },
                    modifier = Modifier.Companion.fillMaxWidth()
                )

                Spacer(modifier = Modifier.Companion.height(12.dp))

                if (error.isNotEmpty()) {
                    Text(error, color = Color.Companion.Red)
                }

                Spacer(modifier = Modifier.Companion.height(16.dp))

                Button(
                    onClick = {
                        if (opponent.isBlank() || date.isBlank()) {
                            error = "Please fill all fields"
                        } else {
                            onStart(opponent, date)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF7A00)   // 🟠 ORANGE
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Start Match", color = Color.Companion.White)
                }
            }
        }
    }
}