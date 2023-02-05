package com.example.domain.models

interface MovieDetails {
    val id: Int
    val nameRu: String?
    val posterUrl: String
    val description: String?
    val countries: List<Countries>
    val genres: List<Genres>
}