package com.example.mymoviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.ItemMovieBinding
import com.example.mymoviesapp.presentation.ui_model.MovieItemUI

class MoviesAdapter(private val movieDetails: (id: Int) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val differ = AsyncListDiffer(this, MoviesDiffUtilCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            movieDetails
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun updateMovieList(newMovieListUI: List<MovieItemUI>) {
        val newList = differ.currentList + newMovieListUI
        differ.submitList(newList)
    }

    class MoviesViewHolder(
        private val itemMovieBinding: ItemMovieBinding,
        private val movieDetails: (id: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemMovieBinding.root) {
        private var id: Int? = null

        init {
            itemView.setOnClickListener {
                id?.let {
                    movieDetails(it)
                }
            }
        }

        fun bind(movieItemUI: MovieItemUI) {
            id = movieItemUI.id
            with(itemMovieBinding) {
                movieTitle.text = movieItemUI.movieTitle
                movieGenreAndYear.text = movieItemUI.movieGenresAndYear

                Glide.with(itemView)
                    .load(movieItemUI.moviePoster)
                    .placeholder(R.drawable.baseline_downloading_24)
                    .error(R.drawable.baseline_file_download_error_24).into(moviePoster)
            }
        }
    }

    class MoviesDiffUtilCallback : DiffUtil.ItemCallback<MovieItemUI>() {
        override fun areItemsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI): Boolean {
            return oldItem == newItem
        }
    }
}