package com.lyhoangvinh.simple

import retrofit2.http.OPTIONS

class Constants {
    companion object {
        const val COMIC_ENDPOINT = BuildConfig.COMIC_ENDPOINT
        const val AVGLE_ENDPOINT = BuildConfig.AVGLE_ENDPOINT
        const val KEY = BuildConfig.API_KEY
        const val EXTRA_DATA = "EXTRA_DATA"

        //video
        const val TYPE_ALL = 0
        const val TYPE_HOME = 1
        const val TYPE_SEARCH = 2

        //collection
        const val TYPE_HOME_BANNER = 1
        const val TYPE_HOME_BOTTOM = 2

        //options
        const val OPTION = "option_open"
        const val OPTION_1 = 0
        const val OPTIONS_2 = 1
        const val OPTIONS_3 = 2
    }
}