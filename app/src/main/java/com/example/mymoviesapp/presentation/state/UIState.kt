package com.example.mymoviesapp.presentation.state

sealed class UIState<out T> where T : Any? {
    object Empty : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class Success<T>(val dataUI: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}