package com.lyhoangvinh.simple.ui.base.interfaces

interface PaginationListener {
   fun getCurrentPage(): Int

   fun setCurrentPage(page: Int)

   fun getPreviousTotal(): Int

   fun setPreviousTotal(previousTotal: Int)

   fun onCallApi(page: Int): Boolean
}