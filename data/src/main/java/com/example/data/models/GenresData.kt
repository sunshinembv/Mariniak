package com.example.data.models

import com.example.domain.models.Genres
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenresData(override val genre: String) : Genres
