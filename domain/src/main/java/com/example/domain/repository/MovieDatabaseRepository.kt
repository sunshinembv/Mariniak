package com.example.domain.repository

import com.example.domain.db_entities.MoviesEntity

interface MovieDatabaseRepository {
    suspend fun insertMovies(movies: List<MoviesEntity>)

    suspend fun getMovies(): List<MoviesEntity>

    suspend fun updateFavoriteMovies(movie: MoviesEntity)

    suspend fun removeFavoriteMovieById(movieId: Long, isFavorite: Boolean)

    suspend fun removePopularMovies(isFavorite: Boolean)
}