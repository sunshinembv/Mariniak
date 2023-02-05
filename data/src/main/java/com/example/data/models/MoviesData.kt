package com.example.data.models

import com.example.domain.models.Films
import com.example.domain.models.Movies
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesData(override val films: List<FilmsData>) : Movies

@JsonClass(generateAdapter = true)
data class FilmsData(
    @Json(name = "filmId")
    override val id: Int,
    override val nameRu: String?,
    override val year: String?,
    override val posterUrlPreview: String?,
    override val genres: List<GenresData>
) : Films