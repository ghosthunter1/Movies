package com.app.movies.ui.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.movies.networking.Resource
import com.app.movies.networking.models.PopularMoviesResponse
import com.app.movies.networking.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val moviesLiveData = MutableLiveData<Resource<PopularMoviesResponse>>()

    fun fetchSimilarMovies(page: Int, movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = moviesRepository.fetchSimilarMovies(movieId,page)
                moviesLiveData.postValue(Resource(Resource.Status.SUCCESS, response))
            } catch (e: Exception) {
                moviesLiveData.postValue(Resource(Resource.Status.ERROR, null,e.message?:"" ))
            }

        }
    }


}