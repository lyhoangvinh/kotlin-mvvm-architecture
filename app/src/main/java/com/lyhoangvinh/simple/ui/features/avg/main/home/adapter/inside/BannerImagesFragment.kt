package com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside

import android.os.Bundle
import android.view.View
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entinies.avgle.Collection
import com.lyhoangvinh.simple.databinding.FragmentBannerImagesBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseFragment
import com.lyhoangvinh.simple.utils.NavigatorHelper
import com.lyhoangvinh.simple.utils.loadImage
import javax.inject.Inject

class BannerImagesFragment : BaseFragment<FragmentBannerImagesBinding>() {

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    companion object {

        fun getInstance(collection: Collection): BannerImagesFragment {
            val bundle = Bundle()
            bundle.putParcelable(Constants.EXTRA_DATA, collection)
            val fragment = BannerImagesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResource() = R.layout.fragment_banner_images

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val collection: Collection = arguments?.getParcelable(Constants.EXTRA_DATA)!!
            val coverUrl: String = collection.coverUrl.toString()
//            val url :String = collection.collectionUrl.toString()
            binding.imv.loadImage(coverUrl)
            binding.imv.setOnClickListener {
//                navigatorHelper.navigateVideosFragment(collection)
            }
        }
    }
}