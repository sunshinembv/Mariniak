package com.example.mymoviesapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetLoadPagesUseCase
import com.example.domain.usecases.GetMoviesFromDbUseCase
import com.example.domain.usecases.GetPopularMoviesUseCase
import com.example.domain.usecases.GetTimeUpdateUseCase
import com.example.domain.usecases.InsertMoviesUseCase
import com.example.domain.usecases.RemoveFavoriteMovieByIdUseCase
import com.example.domain.usecases.RemovePopularMoviesUseCase
import com.example.domain.usecases.SaveLoadPagesUseCase
import com.example.domain.usecases.SaveTimeUpdateUseCase
import com.example.domain.usecases.UpdateToFavoriteMoviesUseCase
import com.example.mymoviesapp.presentation.event.MoviesEvents
import com.example.mymoviesapp.presentation.mapper.MovieEntityMapper
import com.example.mymoviesapp.presentation.mapper.MoviesMapper
import com.example.mymoviesapp.presentation.state.UIState
import com.example.mymoviesapp.presentation.ui_model.MovieDataUI
import com.example.mymoviesapp.utils.toHours
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel(
    private val moviesMapper: MoviesMapper,
    private val movieEntityMapper: MovieEntityMapper,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMoviesFromDbUseCase: GetMoviesFromDbUseCase,
    private val insertMoviesUseCase: InsertMoviesUseCase,
    private val removeFavoriteMovieByIdUseCase: RemoveFavoriteMovieByIdUseCase,
    private val removePopularMoviesUseCase: RemovePopularMoviesUseCase,
    private val updateToFavoriteMoviesUseCase: UpdateToFavoriteMoviesUseCase,
    private val getTimeUpdateUseCase: GetTimeUpdateUseCase,
    private val saveTimeUpdateUseCase: SaveTimeUpdateUseCase,
    private val getLoadPagesUseCase: GetLoadPagesUseCase,
    private val saveLoadPagesUseCase: SaveLoadPagesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<UIState<MovieDataUI>>(UIState.Empty)
    val movies: StateFlow<UIState<MovieDataUI>> = _movies.asStateFlow()

    fun obtainEvent(event: MoviesEvents) {
        when (event) {
            is MoviesEvents.GetMoviesEvent -> loadMovies(
                event.page,
                event.type,
                checkIfNeedUpdate()
            )
            is MoviesEvents.LoadMoreMoviesEvent -> {
                if (!isLastPage()) {
                    loadMovies(event.page, event.type, checkIfNeedUpdate() || !isLastPage())
                }
            }
            is MoviesEvents.AddMovieToFavoriteEvent -> {}
        }
    }

    private fun loadMovies(page: Int, type: String, isNeedUpdate: Boolean) {
        viewModelScope.launch {
            try {
                _movies.emit(UIState.Loading)
                if (checkIfNeedUpdate()) {
                    removePopularMoviesUseCase.execute(false)
                    saveLoadPagesUseCase.execute(LAST_LOAD_PAGE, 0)
                }
                if (isNeedUpdate) {
                    saveTimeUpdateUseCase.execute(LAST_UPDATE_TIMESTAMP, System.currentTimeMillis())
                    saveLoadPagesUseCase.execute(LAST_LOAD_PAGE, page)
                    val movies = getPopularMoviesUseCase.execute(page, type)
                    val moviesUI = moviesMapper.toMovieDataUI(movies)
                    insertMoviesUseCase.execute(movieEntityMapper.movieDataToMovieEntity(movies))
                    _movies.emit(UIState.Success(moviesUI))
                } else {
                    val moviesEntity = getMoviesFromDbUseCase.execute()
                    val moviesUI = movieEntityMapper.movieEntityToMovieDataUI(moviesEntity)
                    _movies.emit(UIState.Success(moviesUI))
                }
            } catch (t: Throwable) {
                Log.d(TAG_ERROR, t.toString())
                _movies.emit(UIState.Error(t.toString()))
            }
        }
    }

    private fun checkIfNeedUpdate(): Boolean {
        val savedTimestamp = getTimeUpdateUseCase.execute(LAST_UPDATE_TIMESTAMP, 0L)
        return (System.currentTimeMillis() - savedTimestamp).toHours > HOURS_WHEN_DATA_FRESH
    }

    private fun isLastPage(): Boolean {
        return getLoadPagesUseCase.execute(LAST_LOAD_PAGE, 1) >= MAX_PAGE
    }

    class Factory @Inject constructor(
        private val moviesMapper: MoviesMapper,
        private val movieEntityMapper: MovieEntityMapper,
        private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
        private val getMoviesFromDbUseCase: GetMoviesFromDbUseCase,
        private val insertMoviesUseCase: InsertMoviesUseCase,
        private val removeFavoriteMovieByIdUseCase: RemoveFavoriteMovieByIdUseCase,
        private val removePopularMoviesUseCase: RemovePopularMoviesUseCase,
        private val updateToFavoriteMoviesUseCase: UpdateToFavoriteMoviesUseCase,
        private val getTimeUpdateUseCase: GetTimeUpdateUseCase,
        private val saveTimeUpdateUseCase: SaveTimeUpdateUseCase,
        private val getLoadPagesUseCase: GetLoadPagesUseCase,
        private val saveLoadPagesUseCase: SaveLoadPagesUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MoviesViewModel::class.java)
            return MoviesViewModel(
                moviesMapper,
                movieEntityMapper,
                getPopularMoviesUseCase,
                getMoviesFromDbUseCase,
                insertMoviesUseCase,
                removeFavoriteMovieByIdUseCase,
                removePopularMoviesUseCase,
                updateToFavoriteMoviesUseCase,
                getTimeUpdateUseCase,
                saveTimeUpdateUseCase,
                getLoadPagesUseCase,
                saveLoadPagesUseCase
            ) as T
        }
    }

    companion object {
        private val TAG_ERROR = MoviesViewModel::class.java.simpleName
        private const val LAST_UPDATE_TIMESTAMP = "LAST_UPDATE_TIMESTAMP"
        private const val LAST_LOAD_PAGE = "LAST_LOAD_PAGE"
        private const val HOURS_WHEN_DATA_FRESH = 4
        private const val MAX_PAGE = 5
    }
}