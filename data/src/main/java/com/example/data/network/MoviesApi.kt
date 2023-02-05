package com.example.data.network

import com.example.data.models.MovieDetailsData
import com.example.data.models.MoviesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("api/v2.2/films/top")
    suspend fun getTopMovies(
        @Query("page") @androidx.annotation.IntRange(from = 1) page: Int = 1,
        @Query("type") type: String
    ): Response<MoviesData>

    @GET("api/v2.2/films/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ): Response<MovieDetailsData>
}