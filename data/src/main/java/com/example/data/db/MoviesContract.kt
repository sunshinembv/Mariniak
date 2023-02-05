package com.example.data.db

object MoviesContract {
    const val MOVIES_TABLE_NAME = "movies"

    object Columns {
        const val ID = "id"
        const val NAME_RU = "name_ru"
        const val YEAR = "year"
        const val POSTER_URL_PREVIEW = "poster_url_preview"
        const val GENRES = "genres"
        const val IS_FAVORITE = "is_favorite"
    }
}