package com.example.domain.usecases

import com.example.domain.db_entities.MoviesEntity
import com.example.domain.repository.MovieDatabaseRepository

class UpdateToFavoriteMoviesUseCase(private val movieDatabaseRepository: MovieDatabaseRepository) {
    suspend fun execute(movie: MoviesEntity) {
        return movieDatabaseRepository.updateFavoriteMovies(movie)
    }
}