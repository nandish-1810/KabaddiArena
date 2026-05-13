package com.example.kabaddiarena.ui.theme

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MatchEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun matchDao(): MatchDao
}