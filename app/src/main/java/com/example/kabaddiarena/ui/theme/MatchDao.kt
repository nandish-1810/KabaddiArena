package com.example.kabaddiarena.ui.theme

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MatchDao {

    @Insert
    suspend fun insertMatch(match: MatchEntity)

    @Query("SELECT * FROM matches")
    suspend fun getAllMatches(): List<MatchEntity>
}