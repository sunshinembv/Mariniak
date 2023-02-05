package com.example.data.models

import com.example.domain.models.Countries
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountriesData(override val country: String) : Countries
