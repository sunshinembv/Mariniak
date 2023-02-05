package com.example.domain.repository

import com.example.domain.models.MovieDetails
import com.example.domain.models.Movies

interface MovieRepository {
    suspend fun getPopularMovies(page: Int, type: String): Movies
    suspend fun getMovieDetails(id: Int): MovieDetails
}