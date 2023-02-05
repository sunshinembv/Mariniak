package com.example.data.models

import com.example.domain.models.MovieDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsData(
    @Json(name = "kinopoiskId")
    override val id: Int,
    override val nameRu: String?,
    override val posterUrl: String,
    override val description: String?,
    override val countries: List<CountriesData>,
    override val genres: List<GenresData>
) : MovieDetails