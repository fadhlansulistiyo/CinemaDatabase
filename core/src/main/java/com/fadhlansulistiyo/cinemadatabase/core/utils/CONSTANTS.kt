package com.fadhlansulistiyo.cinemadatabase.core.utils

import com.fadhlansulistiyo.core.BuildConfig

class CONSTANTS {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original"
        const val DATA_IS_EMPTY = "Data is empty."
        const val NO_INTERNET_CONNECTION = "No internet connection."
        const val NETWORK_ERROR = "Network error."
        const val UNKNOWN_ERROR = "Unknown error."
        const val NA = "N/A"
    }
}