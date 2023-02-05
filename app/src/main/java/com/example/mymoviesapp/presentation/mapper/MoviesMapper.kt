package com.example.mymoviesapp.presentation.mapper

import com.example.domain.models.Genres
import com.example.domain.models.Movies
import com.example.mymoviesapp.presentation.ui_model.MovieDataUI
import com.example.mymoviesapp.presentation.ui_model.MovieItemUI
import javax.inject.Inject

class MoviesMapper @Inject constructor() {

    fun toMovieDataUI(movies: Movies): MovieDataUI {
        val films = movies.films.map { film ->
            MovieItemUI(
                film.id,
                film.nameRu ?: "",
                "${genresToString(film.genres)} (${film.year})",
                film.posterUrlPreview ?: "",
                isFavorite = false
            )
        }
        return MovieDataUI(films)
    }

    private fun genresToString(genres: List<Genres>): String {
        return genres.firstOrNull()?.genre
            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecaseChar() else it } ?: ""
    }
}