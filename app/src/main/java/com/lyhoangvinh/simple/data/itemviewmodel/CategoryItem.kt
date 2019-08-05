package com.lyhoangvinh.simple.data.itemviewmodel

import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class CategoryItem(
    var categories: PagedList<Category>?, override val idViewModel: String?
) : ItemViewModel