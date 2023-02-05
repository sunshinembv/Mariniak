package com.example.domain.usecases

import com.example.domain.db_entities.MoviesEntity
import com.example.domain.repository.MovieDatabaseRepository

class InsertMoviesUseCase(private val movieDatabaseRepository: MovieDatabaseRepository) {
    suspend fun execute(movies: List<MoviesEntity>) {
        return movieDatabaseRepository.insertMovies(movies)
    }
}