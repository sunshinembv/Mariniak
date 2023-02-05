package com.example.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.db.MoviesContract
import com.example.domain.db_entities.MoviesEntity

@Entity(tableName = MoviesContract.MOVIES_TABLE_NAME)
data class MoviesEntityData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MoviesContract.Columns.ID)
    override val id: Int,
    @ColumnInfo(name = MoviesContract.Columns.NAME_RU)
    override val nameRu: String?,
    @ColumnInfo(name = MoviesContract.Columns.YEAR)
    override val year: String?,
    @ColumnInfo(name = MoviesContract.Columns.POSTER_URL_PREVIEW)
    override val posterUrlPreview: String?,
    @ColumnInfo(name = MoviesContract.Columns.GENRES)
    override val genres: String,
    @ColumnInfo(name = MoviesContract.Columns.IS_FAVORITE)
    override val isFavorite: Boolean
): MoviesEntity
