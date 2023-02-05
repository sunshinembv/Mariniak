package com.example.mymoviesapp.presentation.event

sealed class MovieDetailsEvents {
    class GetMovieByIdEvent(val id: Int) : MovieDetailsEvents()
}