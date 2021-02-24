package com.app.movies.ui.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.R
import com.app.movies.base.BaseActivity
import com.app.movies.fromUrl
import com.app.movies.networking.Resource
import com.app.movies.networking.models.Movie
import com.app.movies.ui.movies.MoviesAdapter
import com.app.movies.ui.movies.MoviesViewModel
import com.app.movies.utils.IMAGE_BASE_URL
import com.app.movies.utils.MOVIE_EXTRA_KEY
import com.app.movies.utils.SimpleItemDecoration
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : BaseActivity() {

    private val viewModel: MovieDetailViewModel by viewModel()
    private val adapter = MoviesAdapter(R.layout.similar_movies_list_item)
    private lateinit var movie: Movie
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var pastVisiblesItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var currentPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra(MOVIE_EXTRA_KEY)!!

        iniUI()
        setObservables()
        setListeners()

        viewModel.fetchSimilarMovies(currentPage, movie.id!!.toInt())


    }

    private fun iniUI() {

        linearLayoutManager = movies_recyclerview.layoutManager as LinearLayoutManager
        movies_recyclerview.layoutManager = linearLayoutManager
        movies_recyclerview.addItemDecoration(SimpleItemDecoration())
        PagerSnapHelper().attachToRecyclerView(movies_recyclerview)
        movies_recyclerview.adapter = adapter

        movie_poster.fromUrl(IMAGE_BASE_URL + movie.poster_path)
        movie_title.text = movie.name
        movie_description.text = movie.overview
    }

    private fun setObservables() {
        viewModel.moviesLiveData.observe(this, Observer {
            loading = false
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    adapter.addMovies(it.data!!.results!!)
                }
            }
        })
    }


    private fun setListeners() {
        movies_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                        if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            currentPage++
                            viewModel.fetchSimilarMovies(currentPage, movie.id!!.toInt())
                        }
                }
            }
        })

    }


    companion object {
        fun buildIntent(ctx: Context, movie: Movie): Intent {
            return Intent(ctx, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_EXTRA_KEY, movie)
            }
        }
    }
}