package com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.simple

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.itemviewmodel.*
import com.lyhoangvinh.simple.databinding.*
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BaseItemSimpleAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseItemSimpleViewHolder
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside.CategoriesAdapter
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside.CollectionHomeAdapter
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside.ImageBannerAdapter
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside.VideosHomeAdapter
import com.lyhoangvinh.simple.ui.widget.recycleview.GravitySnapHelper
import com.lyhoangvinh.simple.ui.widget.recycleview.HorizontalSpaceItemDecoration
import com.lyhoangvinh.simple.utils.NavigatorHelper
import com.lyhoangvinh.simple.utils.genericCastOrNull
import com.tmall.ultraviewpager.UltraViewPager
import javax.inject.Inject

class HomeSimpleAdapter @Inject constructor(@ActivityContext context: Context, private val navigatorHelper: NavigatorHelper) :
    BaseItemSimpleAdapter(context, ItemCallback) {

    private var mWidth = 0

    private var mHeight = 0

    fun setLayoutParams(mWidth: Int, mHeight: Int) {
        this.mWidth = mWidth
        this.mHeight = mHeight
    }

    companion object {
        private const val ITEM_SEARCH = 1
        private const val ITEM_CATEGORY = 2
        private const val ITEM_BANNER = 3
        private const val ITEM_VIDEO = 4
        private const val ITEM_COLLECTION_BOTTOM = 5
        private const val ITEM_DIVIDER = 6
        private const val ITEM_TITLE_SEE_ALL = 7
    }

    override fun setItemViewType(item: ItemViewModel): Int {
        return when (item) {
            is SearchItem -> ITEM_SEARCH
            is CategoryItem -> ITEM_CATEGORY
            is CollectionBannerItem -> ITEM_BANNER
            is VideoItem -> ITEM_VIDEO
            is CollectionBottomItem -> ITEM_COLLECTION_BOTTOM
            is DividerItem -> ITEM_DIVIDER
            is TitleSeeAllItem -> ITEM_TITLE_SEE_ALL
            else -> throw RuntimeException("Not support item $item")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            ITEM_SEARCH -> R.layout.item_search
            ITEM_CATEGORY -> R.layout.view_rcy_horizontal
            ITEM_BANNER -> R.layout.view_banner
            ITEM_COLLECTION_BOTTOM -> R.layout.view_rcy_horizontal
            ITEM_VIDEO -> R.layout.view_rcy_horizontal
            ITEM_DIVIDER -> R.layout.view_divider
            ITEM_TITLE_SEE_ALL -> R.layout.item_title_see_all
            else -> throw RuntimeException("Not support layoutResource $viewType")
        }
    }

    override fun createItemViewHolder(
        view: View,
        viewType: Int
    ): BaseItemSimpleViewHolder<ItemViewModel, ViewDataBinding> {
        return when (viewType) {
            ITEM_SEARCH -> genericCastOrNull(SearchItemSimpleViewHolder(view))
            ITEM_CATEGORY -> genericCastOrNull(CategoriesItemSimpleViewHolder(context, view, navigatorHelper))
            ITEM_BANNER -> genericCastOrNull(BannerItemSimpleViewHolder(context, view))
            ITEM_COLLECTION_BOTTOM -> genericCastOrNull(CollectionItemSimpleViewHolder(context, view, mWidth, mHeight, navigatorHelper))
            ITEM_VIDEO -> genericCastOrNull(VideoItemSimpleViewHolder(context, view, mWidth, mHeight, navigatorHelper))
            ITEM_DIVIDER -> genericCastOrNull(DividerItemSimpleViewHolder(view))
            ITEM_TITLE_SEE_ALL -> genericCastOrNull(TitleSeeAllItemViewHolder(view, navigatorHelper))
            else -> throw RuntimeException("Not support type=$viewType")
        }
    }

    private class SearchItemSimpleViewHolder(view: View) : BaseItemSimpleViewHolder<SearchItem, ItemSearchBinding>(view)

    private class DividerItemSimpleViewHolder(view: View) :
        BaseItemSimpleViewHolder<DividerItem, ViewDividerBinding>(view)

    private class TitleSeeAllItemViewHolder(view: View, private val navigatorHelper: NavigatorHelper) :
        BaseItemSimpleViewHolder<TitleSeeAllItem, ItemTitleSeeAllBinding>(view) {
        override fun setItem(data: TitleSeeAllItem, binding: ItemTitleSeeAllBinding) {
            super.setItem(data, binding)
            binding.title = data
            binding.lnSeeAllVideo.setOnClickListener {
                when (data.idViewModel) {
                    "Videos" -> navigatorHelper.navigateVideosFragment()
                    "Collections" -> navigatorHelper.navigateCollectionFragment()
                }
            }
        }
    }

    private class CategoriesItemSimpleViewHolder(private val context: Context, view: View, private val navigatorHelper: NavigatorHelper) :
        BaseItemSimpleViewHolder<CategoryItem, ViewRcyHorizontalBinding>(view) {
        private var isItemDecoration = false
        private var adapter: CategoriesAdapter? = null
        override fun setItem(data: CategoryItem, binding: ViewRcyHorizontalBinding) {
            super.setItem(data, binding)
            if (adapter == null) {
                adapter = CategoriesAdapter(context)
                binding.rcv.adapter = adapter
            }
            if (!isItemDecoration) {
                isItemDecoration = true
                binding.rcv.addItemDecoration(HorizontalSpaceItemDecoration(context.resources.getDimensionPixelSize(R.dimen.padding_10dp)))
            }
//            binding.rcv.isNestedScrollingEnabled = false
            adapter?.submitList(data.categories)
            adapter?.setOnClickItemListener {navigatorHelper.navigateVideosFragment(it)}
            if (binding.rcv.onFlingListener == null) {
                GravitySnapHelper(Gravity.START).attachToRecyclerView(binding.rcv)
            }
        }
    }

    private class BannerItemSimpleViewHolder(private val context: Context, view: View) :
        BaseItemSimpleViewHolder<CollectionBannerItem, ViewBannerBinding>(view) {
        override fun setItem(data: CollectionBannerItem, binding: ViewBannerBinding) {
            super.setItem(data, binding)
            @Suppress("DEPRECATION") val pixel =
                genericCastOrNull<AppCompatActivity>(context).windowManager.defaultDisplay.width / 2
            binding.viewPage.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, pixel)
            binding.viewPage.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
            binding.viewPage.initIndicator()
            binding.viewPage.indicator
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(ContextCompat.getColor(context, android.R.color.white))
                .setNormalColor(ContextCompat.getColor(context, R.color.color_disable_date))
                .setMargin(5, 5, 5, 20)
                .setRadius(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        3f,
                        context.resources.displayMetrics
                    ).toInt()
                )
                .setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
                .build()
            binding.viewPage.setAutoScroll(3000)
            binding.viewPage.adapter = ImageBannerAdapter(
                genericCastOrNull<AppCompatActivity>(context).supportFragmentManager,
                data.collections!!
            )
        }
    }

    private class CollectionItemSimpleViewHolder(
        private val context: Context,
        view: View,
        private val mWidth: Int,
        private val mHeight: Int,
        private val navigatorHelper: NavigatorHelper
    ) :
        BaseItemSimpleViewHolder<CollectionBottomItem, ViewRcyHorizontalBinding>(view) {
        private var isItemDecoration = false
        private var adapter: CollectionHomeAdapter? = null
        override fun setItem(data: CollectionBottomItem, binding: ViewRcyHorizontalBinding) {
            super.setItem(data, binding)
            if (adapter == null) {
                adapter = CollectionHomeAdapter(context).setLayoutParams(mWidth, mHeight)
                binding.rcv.adapter = adapter
            }
            if (!isItemDecoration) {
                isItemDecoration = true
                binding.rcv.addItemDecoration(HorizontalSpaceItemDecoration(context.resources.getDimensionPixelSize(R.dimen.padding_10dp)))
            }
            binding.rcv.isNestedScrollingEnabled = false
            adapter?.submitList(data.collections)
            adapter?.setOnClickItemListener {navigatorHelper.navigateVideosFragment(it)}
            if (binding.rcv.onFlingListener == null) {
                GravitySnapHelper(Gravity.CENTER).attachToRecyclerView(binding.rcv)
            }
        }
    }

    private class VideoItemSimpleViewHolder(
        private val context: Context,
        view: View,
        private val mWidth: Int,
        private val mHeight: Int,
        private val navigatorHelper: NavigatorHelper
    ) :
        BaseItemSimpleViewHolder<VideoItem, ViewRcyHorizontalBinding>(view) {
        private var isItemDecoration = false
        private var adapter: VideosHomeAdapter? = null
        override fun setItem(data: VideoItem, binding: ViewRcyHorizontalBinding) {
            super.setItem(data, binding)
            if (adapter == null) {
                adapter = VideosHomeAdapter(context)
                binding.rcv.adapter = adapter?.setLayoutParams(mWidth, mHeight)
            }
            adapter?.setOnItemClickListener { navigatorHelper.navigateDetailActivity(it) }
            if (!isItemDecoration) {
                isItemDecoration = true
                binding.rcv.addItemDecoration(HorizontalSpaceItemDecoration(context.resources.getDimensionPixelSize(R.dimen.padding_10dp)))
            }
            binding.rcv.isNestedScrollingEnabled = false
            adapter?.submitList(data.videos)
            if (binding.rcv.onFlingListener == null) {
                GravitySnapHelper(Gravity.CENTER).attachToRecyclerView(binding.rcv)
            }
        }
    }

    private object ItemCallback : DiffUtil.ItemCallback<ItemViewModel>() {
        override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is TitleSeeAllItem        && newItem is TitleSeeAllItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is DividerItem            && newItem is DividerItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is SearchItem             && newItem is SearchItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is CategoryItem           && newItem is CategoryItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is CollectionBannerItem   && newItem is CollectionBannerItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is CollectionBottomItem   && newItem is CollectionBottomItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is VideoItem              && newItem is VideoItem -> oldItem.idViewModel == newItem.idViewModel
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is TitleSeeAllItem        && newItem is TitleSeeAllItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is DividerItem            && newItem is DividerItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is SearchItem             && newItem is SearchItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is CategoryItem           && newItem is CategoryItem -> oldItem.categories == newItem.categories
                oldItem is CollectionBannerItem   && newItem is CollectionBannerItem -> oldItem.collections == newItem.collections
                oldItem is CollectionBottomItem   && newItem is CollectionBottomItem -> oldItem.collections == newItem.collections
                oldItem is VideoItem              && newItem is VideoItem -> oldItem.videos == newItem.videos
                else -> false
            }
        }
    }
}