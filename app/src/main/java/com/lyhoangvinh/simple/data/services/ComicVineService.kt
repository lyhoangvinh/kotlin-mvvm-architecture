package com.lyhoangvinh.simple.data.services
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicVineService {

    @GET("issues")
    fun getIssues(
        @Query("limit") limit: Int, @Query("offset") offset: Int
        , @Query("api_key") api_key: String, @Query("format") format: String
        , @Query("sort") sort: String
    ):
            Single<BaseResponseComic<Issues>>
}