package com.example.domain.usecases

import com.example.domain.repository.MovieDatabaseRepository

class RemovePopularMoviesUseCase(private val movieDatabaseRepository: MovieDatabaseRepository) {
    suspend fun execute(isFavorite: Boolean) {
        return movieDatabaseRepository.removePopularMovies(isFavorite)
    }
}