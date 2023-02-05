package com.example.domain.usecases

import com.example.domain.repository.MovieDatabaseRepository

class RemoveFavoriteMovieByIdUseCase(private val movieDatabaseRepository: MovieDatabaseRepository) {
    suspend fun execute(id: Long, isFavorite: Boolean) {
        return movieDatabaseRepository.removeFavoriteMovieById(id, isFavorite)
    }
}