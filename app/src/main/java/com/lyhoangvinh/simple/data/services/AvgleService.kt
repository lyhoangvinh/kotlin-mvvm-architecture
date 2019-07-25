package com.lyhoangvinh.simple.data.services


import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.CategoriesResponse
import com.lyhoangvinh.simple.data.response.CollectionsResponseAvgle
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AvgleService {

    @GET("categories")
    fun getCategories(): Single<BaseResponseAvgle<CategoriesResponse>>

    @GET("collections/{page}")
    fun getCollections(@Path("page") page: Int, @Query("limit") limit: Int): Single<BaseResponseAvgle<CollectionsResponseAvgle>>

    @GET("collections/{keyword}")
    fun getVideoCollections(@Path("keyword") keyword: String): Single<Response<BaseResponseAvgle<CollectionsResponseAvgle>>>

    @GET("videos/{page}")
    fun getVideosFromKeyword(@Path("page") page: Int, @Query("c") chId: String): Single<Response<BaseResponseAvgle<VideosResponseAvgle>>>

    @GET("videos/{page}")
    fun getAllVideos(@Path("page") page: Int): Single<BaseResponseAvgle<VideosResponseAvgle>>

    @GET("search/{query}/{page}")
    fun searchVideos(@Path("query") query: String, @Path("page") page: Int): Single<Response<BaseResponseAvgle<VideosResponseAvgle>>>

    @GET("jav/{query}/{page}")
    fun searchJav(@Path("query") query: String, @Path("page") page: Int): Single<Response<BaseResponseAvgle<VideosResponseAvgle>>>
}