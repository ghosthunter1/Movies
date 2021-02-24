package com.app.movies.ui.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.movies.networking.MoviesApi
import com.app.movies.networking.Resource
import com.app.movies.networking.RetrofitBuilder
import com.app.movies.networking.models.PopularMoviesResponse
import com.app.movies.networking.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import kotlin.Exception


class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val moviesLiveData = MutableLiveData<Resource<PopularMoviesResponse>>()

    fun fetchPopularMovies(page: Int) {
        CoroutineScope(IO).launch {
            try {
                val response = moviesRepository.getPopularMovies(page)
                moviesLiveData.postValue(Resource(Resource.Status.SUCCESS, response))
            } catch (e: Exception) {
                moviesLiveData.postValue(Resource(Resource.Status.ERROR, null,e.message?:"" ))
            }

        }
    }


}

