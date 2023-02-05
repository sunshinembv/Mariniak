package com.example.mymoviesapp.presentation.ui_model

data class MovieItemUI(
    val id: Int,
    val movieTitle: String,
    val movieGenresAndYear: String,
    val moviePoster: String,
    val isFavorite: Boolean
)
