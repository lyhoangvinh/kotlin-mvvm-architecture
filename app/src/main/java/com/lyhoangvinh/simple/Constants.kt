package com.lyhoangvinh.simple

class Constants {
    companion object {
        const val COMIC_ENDPOINT = BuildConfig.COMIC_ENDPOINT
        const val AVGLE_ENDPOINT = BuildConfig.AVGLE_ENDPOINT
        const val KEY = BuildConfig.API_KEY
        const val EXTRA_DATA = "EXTRA_DATA"
        const val PORTAL = "KEYWORD_PORTAL"

        //video
        const val TYPE_ALL = 0
        const val TYPE_HOME = 1
        const val TYPE_SEARCH = 2

        //collection
        const val TYPE_HOME_BANNER = 1
        const val TYPE_HOME_BOTTOM = 2

        //options
        const val OPTIONS = "option_open"
        const val OPTIONS_1 = 0
        const val OPTIONS_2 = 1
        const val OPTIONS_3 = 2

        //quality
        const val THUMB = 0
        const val MEDIUM = 1
        const val SUPPER = 2
    }
}