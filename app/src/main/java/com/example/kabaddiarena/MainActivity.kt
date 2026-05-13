package com.example.kabaddiarena

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val firestore = FirebaseFirestore.getInstance()

            var screen by remember {
                mutableStateOf("home")
            }

            var opponent by remember {
                mutableStateOf("")
            }

            var date by remember {
                mutableStateOf("")
            }

            var actions by remember {
                mutableStateOf(listOf<ActionType>())
            }

            var matches by remember {
                mutableStateOf(listOf<Match>())
            }

            val context = LocalContext.current

            // LOAD MATCH HISTORY
            LaunchedEffect(Unit) {

                firestore.collection("matches")
                    .get()
                    .addOnSuccessListener { result ->

                        val loadedMatches =
                            mutableListOf<Match>()

                        for (document in result) {

                            loadedMatches.add(
                                Match(
                                    id = document.id,
                                    opponent =
                                        document.getString("opponent")
                                            ?: "",

                                    date =
                                        document.getString("date")
                                            ?: ""
                                )
                            )
                        }

                        matches = loadedMatches
                    }
            }

            when (screen) {

                // HOME SCREEN
                "home" -> HomeScreen(

                    matches = matches,

                    onStartNew = {
                        screen = "setup"
                    },

                    onDeleteMatch = { match ->

                        firestore.collection("matches")
                            .document(match.id)
                            .delete()

                        matches = matches.filter {
                            it.id != match.id
                        }
                    }
                )

                // MATCH SETUP
                "setup" -> MatchSetupScreen { o, d ->

                    opponent = o
                    date = d

                    screen = "live"
                }

                // LIVE MATCH
                "live" -> LiveMatchScreen(
                    opponent,
                    date
                ) { act ->

                    actions = act

                    val matchData = hashMapOf(
                        "opponent" to opponent,
                        "date" to date
                    )

                    firestore.collection("matches")
                        .add(matchData)
                        .addOnSuccessListener { document ->

                            matches = matches + Match(
                                id = document.id,
                                opponent = opponent,
                                date = date
                            )

                            screen = "result"
                        }
                }

                // RESULT SCREEN
                "result" -> {

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

                    ResultScreen(

                        actions = actions,

                        touchPoints = touchPoints,

                        emptyRaids = emptyRaids,

                        superTackles = superTackles,

                        bonusPoints = bonusPoints,

                        tackles = tackles,

                        onRestart = {
                            screen = "home"
                        },

                        onWatchVideo = {

                            val intent =
                                Intent(Intent.ACTION_VIEW)

                            intent.data = Uri.parse(
                                "https://www.youtube.com/watch?v=8s7r9Y9o8p8"
                            )

                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}