package com.lyhoangvinh.simple.ui.features.comicavg.portal

import android.content.Context
import android.view.View
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentPortalBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelFragment

class PortalFragment : BaseViewModelFragment<FragmentPortalBinding, PortalViewModel>() {
    override fun getLayoutResource() = R.layout.fragment_portal
    override fun createViewModelClass() = PortalViewModel::class.java
    override fun initialize(view: View, ctx: Context?) {
    }
}