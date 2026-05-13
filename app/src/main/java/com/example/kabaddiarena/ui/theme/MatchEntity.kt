package com.example.kabaddiarena.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "matches")
data class MatchEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val opponent: String,
    val date: String,
    val actionsCount: Int
)