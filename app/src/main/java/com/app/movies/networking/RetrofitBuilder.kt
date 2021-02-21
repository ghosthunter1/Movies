package com.app.movies.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(private  val defaultParameterInterceptor: DefaultParameterInterceptor) {
    private val BASE_URL = "https://api.themoviedb.org/3/tv/"
    private var retrofitInctance: Retrofit? = null


    private fun getRetrofitInctance(): Retrofit {
        val hhtpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(hhtpLoggingInterceptor)
            .addInterceptor(defaultParameterInterceptor)
            .build()

        if (retrofitInctance == null){
            retrofitInctance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofitInctance!!
    }

    fun getApi(): MoviesApi {
        return getRetrofitInctance().create(MoviesApi::class.java)
    }
}