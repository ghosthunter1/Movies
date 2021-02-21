package com.app.movies.networking.repositories

import com.app.movies.networking.RetrofitBuilder
import com.app.movies.networking.models.PopularMoviesResponse

class MoviesRepository(val retrofitBuilder: RetrofitBuilder) {

    suspend fun getPopularMovies(page: Int): PopularMoviesResponse? {
        return retrofitBuilder.getApi().getMovies(page)
    }

    suspend fun fetchSimilarMovies(page: Int, movieId: Int): PopularMoviesResponse? {
        return retrofitBuilder.getApi().fetchSimilarMovies(page, movieId)
    }
}