package com.lyhoangvinh.simple.ui.features.comicavg

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lyhoangvinh.simple.ui.features.avg.main.home.HomeFragment
import com.lyhoangvinh.simple.ui.features.comicavg.portal.PortalFragment

class ComicAvgPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HOME -> HomeFragment()
            else -> PortalFragment()
        }
    }

    override fun getItemCount() = 2

    companion object {
        const val HOME = 0
    }
}