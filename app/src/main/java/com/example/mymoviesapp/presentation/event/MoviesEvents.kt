package com.example.mymoviesapp.presentation.event

sealed class MoviesEvents {
    class GetMoviesEvent(val page: Int, val type: String) : MoviesEvents()
    class LoadMoreMoviesEvent(val page: Int, val type: String) : MoviesEvents()
    class AddMovieToFavoriteEvent(val id: Int) : MoviesEvents()
}