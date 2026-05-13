package com.example.kabaddiarena

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material3.*
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class Match(
    val id: String = "",
    val opponent: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    matches: List<Match>,
    onStartNew: () -> Unit,
    onDeleteMatch: (Match) -> Unit
) {

    val matchList = remember {
        mutableStateListOf(*matches.toTypedArray())
    }

    val scale by animateFloatAsState(
        targetValue = 1f,
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F2027),
                        Color(0xFF203A43),
                        Color(0xFF2C5364)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Kabaddi Arena",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Track your performance like a pro",
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Match History",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                )
            ) {

                if (matchList.isEmpty()) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "No Matches Yet",
                            color = Color.White
                        )

                        Text(
                            text = "Start a new match",
                            color = Color.LightGray
                        )
                    }

                } else {

                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        itemsIndexed(matchList) { index, match ->

                            val dismissState =
                                rememberSwipeToDismissBoxState(
                                    confirmValueChange = { value ->

                                        if (value ==
                                            SwipeToDismissBoxValue.EndToStart
                                        ) {

                                            onDeleteMatch(match)
                                            matchList.removeAt(index)
                                            true

                                        } else {
                                            false
                                        }
                                    }
                                )

                            SwipeToDismissBox(
                                state = dismissState,

                                enableDismissFromStartToEnd = false,

                                backgroundContent = {

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Red)
                                            .padding(end = 20.dp),

                                        contentAlignment =
                                            Alignment.CenterEnd
                                    ) {

                                        Text(
                                            text = "Delete",
                                            color = Color.White
                                        )
                                    }
                                }
                            ) {

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),

                                    colors = CardDefaults.cardColors(
                                        containerColor =
                                            Color.White.copy(alpha = 0.08f)
                                    ),

                                    shape = RoundedCornerShape(16.dp)
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),

                                        verticalAlignment =
                                            Alignment.CenterVertically
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.SportsKabaddi,

                                            contentDescription = null,

                                            tint = Color(0xFFFFA000)
                                        )

                                        Spacer(
                                            modifier = Modifier.width(12.dp)
                                        )

                                        Column {

                                            Text(
                                                text = match.opponent,
                                                color = Color.White
                                            )

                                            Text(
                                                text = match.date,
                                                color = Color.LightGray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onStartNew,

            containerColor = Color(0xFFFF7A00),

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .scale(scale)
        ) {

            Text(
                text = "+",
                color = Color.White
            )
        }
    }
}