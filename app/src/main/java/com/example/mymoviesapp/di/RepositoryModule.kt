package com.example.mymoviesapp.di

import com.example.data.repository.AppInfoRepositoryImpl
import com.example.data.repository.MovieDatabaseRepositoryImpl
import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.repository.AppInfoRepository
import com.example.domain.repository.MovieDatabaseRepository
import com.example.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun provideMovieRepositoryImpl(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    fun provideMovieDatabaseRepositoryImpl(impl: MovieDatabaseRepositoryImpl): MovieDatabaseRepository

    @Binds
    fun provideAppInfoRepositoryImpl(impl: AppInfoRepositoryImpl): AppInfoRepository
}