package com.app.movies.ui.movies

import android.app.Activity
import android.app.ActivityOptions
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.R
import com.app.movies.networking.models.Movie
import com.app.movies.fromUrl
import com.app.movies.ui.moviedetail.MovieDetailActivity
import com.app.movies.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.movies_list_item.view.*

class MoviesAdapter(val layout: Int) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val moviesList = arrayListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    fun addMovies(movies: List<Movie>) {
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }


    inner class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            view.moview_image.fromUrl(IMAGE_BASE_URL + movie.poster_path)
            view.moview_title.text = movie.name
            view.moview_rating.rating = movie.vote_average!!.toFloat()
            view.moview_desc.text = movie.overview
        }

        override fun onClick(p0: View?) {
            val ctx = view.context
            val pair = Pair<View, String>(view.moview_image, "image")
            val pair2 = Pair<View, String>(view.moview_title, "title")

            val options = ActivityOptions
                .makeSceneTransitionAnimation(ctx as AppCompatActivity?, pair, pair2)
            ctx.startActivity(
                MovieDetailActivity.buildIntent(ctx, moviesList[adapterPosition]),
                options.toBundle()
            )
        }
    }

}