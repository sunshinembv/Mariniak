package com.example.mymoviesapp.presentation.ui_model

data class MovieDetailsDataUI(
    val id: Int,
    val movieTitle: String,
    val movieDescription: String,
    val movieGenres: String,
    val movieCountryOfOrigin: String,
    val moviePoster: String
)