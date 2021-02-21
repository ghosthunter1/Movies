package com.app.movies.networking

import com.app.movies.networking.models.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("popular")
    suspend fun getMovies(@Query("page") page: Int): PopularMoviesResponse?

    @GET("{id}/similar")
    suspend fun fetchSimilarMovies(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): PopularMoviesResponse?
}