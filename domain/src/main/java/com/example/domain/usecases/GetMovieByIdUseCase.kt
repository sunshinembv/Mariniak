package com.example.domain.usecases

import com.example.domain.models.MovieDetails
import com.example.domain.repository.MovieRepository

class GetMovieByIdUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(id: Int): MovieDetails {
        return movieRepository.getMovieDetails(id)
    }
}