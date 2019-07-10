package com.lyhoangvinh.simple.ui.features.test

import android.os.Bundle
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.ui.base.BaseViewModel
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import javax.inject.Inject

class MainViewModel @Inject constructor(private val comicVineService: ComicVineService) : BaseViewModel() {

    var text: String = ""

    fun clickOK() {
        execute(true, comicVineService.getIssues(
            100, 0, Constants.KEY,
            "json",
            "cover_date: desc"
        ), object : PlainConsumer<BaseResponseComic<Issues>> {
            override fun accept(t: BaseResponseComic<Issues>) {
                text = "Size ${t.number_of_page_results}"
            }
        })
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {}
}