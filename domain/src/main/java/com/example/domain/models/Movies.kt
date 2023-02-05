package com.example.domain.models

interface Movies {
    val films: List<Films>
}

interface Films {
    val id: Int
    val nameRu: String?
    val year: String?
    val posterUrlPreview: String?
    val genres: List<Genres>
}
