package com.example.mymoviesapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentMoviesBinding
import com.example.mymoviesapp.decoration.BottomItemDecoration
import com.example.mymoviesapp.presentation.adapter.EndlessRecyclerViewScrollListener
import com.example.mymoviesapp.presentation.adapter.MoviesAdapter
import com.example.mymoviesapp.presentation.event.MoviesEvents
import com.example.mymoviesapp.presentation.state.UIState
import com.example.mymoviesapp.presentation.ui_model.MovieDataUI
import com.example.mymoviesapp.presentation.view_model.MoviesViewModel
import com.example.mymoviesapp.utils.appComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private val viewBinding by viewBinding(FragmentMoviesBinding::bind)

    @Inject
    lateinit var moviesViewModelFactory: dagger.Lazy<MoviesViewModel.Factory>

    private val viewModel: MoviesViewModel by viewModels {
        moviesViewModelFactory.get()
    }

    private var moviesAdapter: MoviesAdapter? = null
    private var searchView: SearchView? = null

    private var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initMenu()
        initButtons()
        initToolbar()
        render()
        viewModel.obtainEvent(MoviesEvents.GetMoviesEvent(initial_page, TYPE))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        moviesAdapter = null
        searchView = null
        endlessRecyclerViewScrollListener = null
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        endlessRecyclerViewScrollListener = object :
            EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadNextData(page)
            }
        }
        moviesAdapter = MoviesAdapter(movieDetails = { id ->
            val bundle = MovieDetailsFragment.args(id)
            findNavController().navigate(
                R.id.action_moviesFragment_to_movieDetailsFragment2,
                bundle
            )
        })
        with(viewBinding.moviesList) {
            addOnScrollListener(endlessRecyclerViewScrollListener as EndlessRecyclerViewScrollListener)
            adapter = moviesAdapter
            layoutManager = linearLayoutManager

            addItemDecoration(BottomItemDecoration())
        }
    }

    private fun initMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.search)
                searchView = searchItem.actionView as SearchView
                searchView?.let { searchView ->
                    //searchCityViewModel.searchCity(it.queryTextFlow())
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> true
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initButtons() {
        with(viewBinding) {
            favoritesMoviesBtn.setOnClickListener {
                changeToolBarTitle(R.string.favorites)
            }

            popularMoviesBtn.setOnClickListener {
                changeToolBarTitle(R.string.popular)
            }

            itemLoadingError.repeatBtn.setOnClickListener {
                endlessRecyclerViewScrollListener?.resetState()
                viewModel.obtainEvent(MoviesEvents.GetMoviesEvent(initial_page, TYPE))
            }
        }
    }

    private fun initToolbar() {
        viewBinding.toolbar.inflateMenu(R.menu.search_menu)
    }

    private fun render() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.flowWithLifecycle(
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

    private fun setData(uiState: UIState.Success<MovieDataUI>) {
        moviesAdapter?.updateMovieList(uiState.dataUI.movies)
    }

    private fun viewVisibility(uiState: UIState<MovieDataUI>) {
        with(viewBinding) {
            moviesList.isVisible = uiState is UIState.Success || getAdapterChildCount() > 0
            favoritesMoviesBtn.isVisible = uiState is UIState.Success || getAdapterChildCount() > 0
            popularMoviesBtn.isVisible = uiState is UIState.Success || getAdapterChildCount() > 0
            itemLoadingError.itemLoadingError.isVisible = uiState is UIState.Error
            progressBar.isVisible = uiState is UIState.Loading && getAdapterChildCount() <= 0
            notFoundBtn.isVisible = uiState is UIState.Empty
        }
    }

    private fun changeToolBarTitle(@StringRes strId: Int) {
        viewBinding.toolbar.setTitle(strId)
    }

    private fun loadNextData(page: Int) {
        if (page <= MAX_PAGE) {
            viewModel.obtainEvent(MoviesEvents.LoadMoreMoviesEvent(page, TYPE))
        }
    }

    private fun getAdapterChildCount(): Int {
        return moviesAdapter?.itemCount ?: 0
    }

    companion object {
        private const val TYPE = "TOP_100_POPULAR_FILMS"
        private var initial_page = 1
        private const val MAX_PAGE = 5
    }
}