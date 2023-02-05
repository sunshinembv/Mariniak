package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.MoviesDatabase.Companion.DB_VERSION
import com.example.data.db.entities.MoviesEntityData

@Database(
    entities = [MoviesEntityData::class],
    version = DB_VERSION
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "movie-db"
    }
}