package com.example.mymoviesapp.presentation.mapper

import com.example.domain.models.Countries
import com.example.domain.models.Genres
import com.example.domain.models.MovieDetails
import com.example.mymoviesapp.presentation.ui_model.MovieDetailsDataUI
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() {

    fun toMovieDetailsDataUI(movie: MovieDetails): MovieDetailsDataUI {
        return MovieDetailsDataUI(
            movie.id,
            movie.nameRu ?: "",
            movie.description ?: "",
            genresToString(movie.genres),
            countriesToString(movie.countries),
            movie.posterUrl
        )
    }

    private fun genresToString(genres: List<Genres>): String {
        return genres.map {
            it.genre
        }.toString().replace("[", "").replace("]", "")
    }

    private fun countriesToString(countries: List<Countries>): String {
        return countries.map {
            it.country
        }.toString().replace("[", "").replace("]", "")
    }
}