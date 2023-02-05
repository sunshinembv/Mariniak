package com.example.domain.db_entities

interface MoviesEntity {
    val id: Int
    val nameRu: String?
    val year: String?
    val posterUrlPreview: String?
    val genres: String
    val isFavorite: Boolean
}