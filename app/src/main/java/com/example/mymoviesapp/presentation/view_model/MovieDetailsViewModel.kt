package com.example.mymoviesapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetMovieByIdUseCase
import com.example.mymoviesapp.presentation.event.MovieDetailsEvents
import com.example.mymoviesapp.presentation.mapper.MovieDetailsMapper
import com.example.mymoviesapp.presentation.state.UIState
import com.example.mymoviesapp.presentation.ui_model.MovieDetailsDataUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel(
    private val movieDetailsMapper: MovieDetailsMapper,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<UIState<MovieDetailsDataUI>>(UIState.Empty)
    val movieDetails: StateFlow<UIState<MovieDetailsDataUI>> = _movieDetails.asStateFlow()

    fun obtainEvent(event: MovieDetailsEvents) {
        when (event) {
            is MovieDetailsEvents.GetMovieByIdEvent -> loadMovieDetails(event.id)
        }
    }

    private fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                _movieDetails.emit(UIState.Loading)
                val movie = movieDetailsMapper.toMovieDetailsDataUI(getMovieByIdUseCase.execute(id))
                _movieDetails.emit(UIState.Success(movie))
            } catch (t: Throwable) {
                Log.d(TAG_ERROR, t.toString())
                _movieDetails.emit(UIState.Error(t.toString()))
            }
        }
    }

    class Factory @Inject constructor(
        private val movieDetailsMapper: MovieDetailsMapper,
        private val getMovieByIdUseCase: GetMovieByIdUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MovieDetailsViewModel::class.java)
            return MovieDetailsViewModel(
                movieDetailsMapper,
                getMovieByIdUseCase
            ) as T
        }
    }

    companion object {
        private val TAG_ERROR = MovieDetailsViewModel::class.java.simpleName
    }
}