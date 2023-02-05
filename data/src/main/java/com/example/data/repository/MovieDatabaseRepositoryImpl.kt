package com.example.data.repository

import com.example.data.db.MoviesDatabase
import com.example.data.db.entities.MoviesEntityData
import com.example.domain.db_entities.MoviesEntity
import com.example.domain.repository.MovieDatabaseRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MovieDatabaseRepositoryImpl @Inject constructor(
    database: MoviesDatabase
) : MovieDatabaseRepository {

    private val dao = database.moviesDao()

    override suspend fun insertMovies(movies: List<MoviesEntity>) {
        dao.insertMovies(movies as List<MoviesEntityData>)
    }

    override suspend fun getMovies(): List<MoviesEntity> {
        return dao.getMovies()
    }

    override suspend fun updateFavoriteMovies(movie: MoviesEntity) {
        dao.updateFavoriteMovies(movie as MoviesEntityData)
    }

    override suspend fun removeFavoriteMovieById(movieId: Long, isFavorite: Boolean) {
        dao.removeFavoriteMovieById(movieId, isFavorite)
    }

    override suspend fun removePopularMovies(isFavorite: Boolean) {
        dao.removePopularMovies(isFavorite)
    }
}