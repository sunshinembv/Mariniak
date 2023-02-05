package com.example.mymoviesapp.presentation.mapper

import com.example.data.db.entities.MoviesEntityData
import com.example.domain.db_entities.MoviesEntity
import com.example.domain.models.Genres
import com.example.domain.models.Movies
import com.example.mymoviesapp.presentation.ui_model.MovieDataUI
import com.example.mymoviesapp.presentation.ui_model.MovieItemUI
import javax.inject.Inject

class MovieEntityMapper @Inject constructor() {

    fun movieDataToMovieEntity(movies: Movies): List<MoviesEntity> {
        return movies.films.map { films ->
            MoviesEntityData(
                films.id,
                films.nameRu,
                films.year,
                films.posterUrlPreview,
                genresToString(films.genres),
                isFavorite = false
            )
        }
    }

    fun movieEntityToMovieDataUI(movieEntity: List<MoviesEntity>): MovieDataUI {
        val films = movieEntity.map { film ->
            MovieItemUI(
                film.id,
                film.nameRu ?: "",
                "${film.genres} (${film.year})",
                film.posterUrlPreview ?: "",
                film.isFavorite
            )
        }
        return MovieDataUI(films)
    }

    private fun genresToString(genres: List<Genres>): String {
        return genres.map {
            it.genre
        }.toString().replace("[", "").replace("]", "")
    }
}