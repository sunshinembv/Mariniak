package com.example.domain.usecases

import com.example.domain.models.Movies
import com.example.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(page: Int, type: String): Movies {
        return movieRepository.getPopularMovies(page, type)
    }
}