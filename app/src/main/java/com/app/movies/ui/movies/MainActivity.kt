package com.app.movies.ui.movies

import android.os.Bundle
import android.transition.Fade
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.R
import com.app.movies.base.BaseActivity
import com.app.movies.networking.Resource
import com.app.movies.utils.SimpleItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val viewModel: MoviesViewModel by viewModel()
    private val adapter = MoviesAdapter(R.layout.movies_list_item)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var pastVisiblesItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var currentPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniUI();
        setListeners()
        setObservables();

        viewModel.fetchPopularMovies(currentPage)

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

    private fun iniUI() {
        linearLayoutManager = movies_recyclerview.layoutManager as LinearLayoutManager
        movies_recyclerview.layoutManager = linearLayoutManager
        movies_recyclerview.addItemDecoration(SimpleItemDecoration())
        movies_recyclerview.adapter = adapter
    }

    private fun setListeners() {
        movies_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            currentPage++
                            viewModel.fetchPopularMovies(currentPage)
                        }
                    }
                }
            }
        })

    }

}
