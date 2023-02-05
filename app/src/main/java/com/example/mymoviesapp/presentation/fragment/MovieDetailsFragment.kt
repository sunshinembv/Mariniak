package com.example.mymoviesapp.presentation.fragment

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentMovieDetailsBinding
import com.example.mymoviesapp.presentation.event.MovieDetailsEvents
import com.example.mymoviesapp.presentation.state.UIState
import com.example.mymoviesapp.presentation.ui_model.MovieDetailsDataUI
import com.example.mymoviesapp.presentation.view_model.MovieDetailsViewModel
import com.example.mymoviesapp.utils.appComponent
import com.example.mymoviesapp.utils.setTypeface
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val viewBinding by viewBinding(FragmentMovieDetailsBinding::bind)

    @Inject
    lateinit var movieDetailsViewModelFactory: dagger.Lazy<MovieDetailsViewModel.Factory>

    private val viewModel: MovieDetailsViewModel by viewModels {
        movieDetailsViewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getInt(MOVIE_ID)
        initToolbar()
        initButtons(id)
        render()
        viewModel.obtainEvent(MovieDetailsEvents.GetMovieByIdEvent(id))
    }

    private fun render() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieDetails.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            ).collect { uiState ->
                when (uiState) {
                    is UIState.Success -> {
                        setData(uiState)
                        viewVisibility(uiState)
                    }
                    is UIState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            uiState.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                        viewVisibility(uiState)
                    }
                    is UIState.Loading -> viewVisibility(uiState)
                    is UIState.Empty -> viewVisibility(uiState)
                }
            }
        }
    }

    private fun setData(uiState: UIState.Success<MovieDetailsDataUI>) {
        with(viewBinding) {
            movieTitle.text = uiState.dataUI.movieTitle
            movieDescription.text = uiState.dataUI.movieDescription
            movieGenre.text =
                getString(R.string.genres, uiState.dataUI.movieGenres).setTypeface(Typeface.BOLD)
            movieCountryOrigin.text = getString(
                R.string.сountry_of_origin,
                uiState.dataUI.movieCountryOfOrigin
            ).setTypeface(Typeface.BOLD)
            movieTitle.text = uiState.dataUI.movieTitle

            Glide.with(requireContext())
                .load(uiState.dataUI.moviePoster)
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_file_download_error_24).into(moviePoster)
        }
    }

    private fun viewVisibility(uiState: UIState<MovieDetailsDataUI>) {
        with(viewBinding) {
            toolbar.isVisible = true
            moviePoster.isVisible = uiState is UIState.Success
            movieTitle.isVisible = uiState is UIState.Success
            movieDescription.isVisible = uiState is UIState.Success
            movieGenre.isVisible = uiState is UIState.Success
            movieCountryOrigin.isVisible = uiState is UIState.Success
            itemLoadingError.itemLoadingError.isVisible = uiState is UIState.Error
            progressBar.isVisible = uiState is UIState.Loading
        }
    }

    private fun initToolbar() {
        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initButtons(id: Int) {
        viewBinding.itemLoadingError.repeatBtn.setOnClickListener {
            viewModel.obtainEvent(MovieDetailsEvents.GetMovieByIdEvent(id))
        }
    }

    companion object {
        private const val MOVIE_ID = "movieId"
        fun args(id: Int): Bundle {
            return Bundle().apply {
                putInt(MOVIE_ID, id)
            }
        }
    }
}