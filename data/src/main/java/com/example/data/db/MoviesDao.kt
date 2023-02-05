package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.db.entities.MoviesEntityData

@Dao
interface MoviesDao {
    @Insert
    suspend fun insertMovies(movies: List<MoviesEntityData>)

    @Query("SELECT * FROM ${MoviesContract.MOVIES_TABLE_NAME}")
    suspend fun getMovies(): List<MoviesEntityData>

    @Update
    suspend fun updateFavoriteMovies(movie: MoviesEntityData)

    @Query("DELETE FROM ${MoviesContract.MOVIES_TABLE_NAME} WHERE ${MoviesContract.Columns.ID} = :movieId and ${MoviesContract.Columns.IS_FAVORITE} = :isFavorite")
    suspend fun removeFavoriteMovieById(movieId: Long, isFavorite: Boolean)

    @Query("DELETE FROM ${MoviesContract.MOVIES_TABLE_NAME} WHERE ${MoviesContract.Columns.IS_FAVORITE} = :isFavorite")
    suspend fun removePopularMovies(isFavorite: Boolean)
}