package com.app.movies.networking.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Long>?,
    val id: Long?,
    val name: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Long?
) : Parcelable
