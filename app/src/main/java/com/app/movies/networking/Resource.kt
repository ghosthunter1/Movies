package com.app.movies.networking

import java.io.Serializable

class Resource<T> (
    val status: Status? = null,
    val data: T? = null,
    val message: String? = null
): Serializable {

    enum class Status {
        SUCCESS, ERROR
    }
}