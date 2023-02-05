package com.example.domain.usecases

import com.example.domain.db_entities.MoviesEntity
import com.example.domain.repository.MovieDatabaseRepository

class GetMoviesFromDbUseCase(private val movieDatabaseRepository: MovieDatabaseRepository) {
    suspend fun execute(): List<MoviesEntity> {
        return movieDatabaseRepository.getMovies()
    }
}