package com.lyhoangvinh.simple.ui.features.comicavg

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.ui.features.comicavg.portal.PortalFragment

class ComicAvgPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var categories: List<Category> = listOf()

    override fun createFragment(position: Int): Fragment {
        return PortalFragment.newInstance(categories[position % categories.size].name.toString())
    }

    override fun getItemCount() = when {
        categories.size > 1 -> categories.size * 10000
        categories.size == 1 -> categories.size
        else -> 0
    }

    fun submitData(newList: List<Category>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(categories, newList), false)
        this.categories = newList
        diffResult.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                notifyItemRangeChanged(position, count, payload)
            }
        })
    }

    fun getCenterPosition(position: Int): Int {
        return categories.size * 10000 / 2 + position
    }

    class DiffCallBack(
        private val current: List<Category>,
        private val next: List<Category>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return current.size
        }

        override fun getNewListSize(): Int {
            return next.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return current[oldItemPosition] == next[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return current[oldItemPosition] == next[newItemPosition]
        }
    }
}