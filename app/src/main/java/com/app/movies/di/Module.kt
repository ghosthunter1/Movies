package com.app.movies.di

import com.app.movies.networking.DefaultParameterInterceptor
import com.app.movies.networking.MoviesApi
import com.app.movies.networking.RetrofitBuilder
import com.app.movies.networking.repositories.MoviesRepository
import com.app.movies.ui.moviedetail.MovieDetailViewModel
import com.app.movies.ui.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {

    single {
        DefaultParameterInterceptor()
    }

    single {
        RetrofitBuilder(get())
    }

    single {
        MoviesRepository(get())
    }

    viewModel {
        MoviesViewModel(get())
    }

    viewModel {
        MovieDetailViewModel(get())
    }
}