package com.example.mymoviesapp.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(context, MoviesDatabase::class.java, MoviesDatabase.DB_NAME)
            .build()
    }
}