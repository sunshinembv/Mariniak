package com.example.data.repository

import com.example.data.network.MoviesApi
import com.example.data.network.NetworkHandleService
import com.example.domain.models.MovieDetails
import com.example.domain.models.Movies
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val networkHandleService: NetworkHandleService,
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int, type: String): Movies {
        return networkHandleService.apiCall { api.getTopMovies(page, type) }
    }

    override suspend fun getMovieDetails(id: Int): MovieDetails {
        return networkHandleService.apiCall { api.getMovieDetails(id) }
    }
}
