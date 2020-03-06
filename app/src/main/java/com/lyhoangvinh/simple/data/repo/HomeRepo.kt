package com.lyhoangvinh.simple.data.repo

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.CategoriesDao
import com.lyhoangvinh.simple.data.dao.CollectionDao
import com.lyhoangvinh.simple.data.dao.VideosDao
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.itemviewmodel.*
import com.lyhoangvinh.simple.data.response.*
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.PlainResponseFourConsumer
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.genericCastOrNull
import com.lyhoangvinh.simple.utils.newPlainConsumer
import com.lyhoangvinh.simple.utils.newPlainResponseFourConsumer
import io.reactivex.Flowable
import javax.inject.Inject

class HomeRepo @Inject constructor(
    private val avgleService: AvgleService,
    private val categoriesDao: CategoriesDao,
    private val collectionDao: CollectionDao,
    private val videosDao: VideosDao
) : BaseRepo() {

    /**
     * Use MediatorLiveData To Query And Merge Multiple Data Source Type Into Single LiveData
     * link "https://code.luasoftware.com/tutorials/android/use-mediatorlivedata-to-query-and-merge-different-data-type/"
     */

    fun fetchData(): LiveData<List<ItemViewModel>> {
        val liveDataMerger = MediatorLiveData<MergedData>()
        liveDataMerger.addSource(
            LivePagedListBuilder(
                categoriesDao.liveDataFactory(),
                10
            ).build()
        ) {
            liveDataMerger.value = CategoryData(it!!)
        }
        liveDataMerger.addSource(collectionDao.liveDataFromType(Constants.TYPE_HOME_BANNER)) {
            liveDataMerger.value = CollectionBannerData(it!!)
        }
        liveDataMerger.addSource(
            LivePagedListBuilder(
                collectionDao.liveDataFactoryFromType(Constants.TYPE_HOME_BOTTOM),
                10
            ).build()
        ) {
            liveDataMerger.value = CollectionBottomData(it!!)
        }
        liveDataMerger.addSource(
            LivePagedListBuilder(
                videosDao.liveDataFactoryFromType(Constants.TYPE_HOME),
                10
            ).build()
        ) {
            liveDataMerger.value = VideoData(it!!)
        }
        val pagedList = ArrayList<ItemViewModel>()
        pagedList.add(SearchItem("SearchItem"))
        return Transformations.switchMap(liveDataMerger, Function {
            val liveData = SafeMutableLiveData<List<ItemViewModel>>()
            when (it) {
                is CategoryData -> pagedList.add(
                    CategoryItem(
                        it.categoryItems,
                        "CategoryItem ${it.categoryItems.size}"
                    )
                )
                is CollectionBannerData -> pagedList.add(
                    CollectionBannerItem(
                        it.collectionBannerItems,
                        "CollectionBannerItem ${it.collectionBannerItems.size}"
                    )
                )
                is CollectionBottomData -> pagedList.add(
                    CollectionBottomItem(
                        it.collectionBottomItems,
                        "CollectionBottomItem ${it.collectionBottomItems.size}"
                    )
                )
                is VideoData -> pagedList.add(
                    VideoItem(
                        it.videoItems,
                        "VideoItem ${it.videoItems.size}"
                    )
                )
            }
            liveData.setValue(pagedList)
            return@Function liveData
        })
    }

    fun liveDataHome(): LiveData<List<ItemViewModel>> {
        return Transformations.map(fetchData()) {
            val newList = ArrayList<ItemViewModel>()
            var searchItem = SearchItem(null)
            var categoryItem = CategoryItem(null, null)
            var collectionBannerItem = CollectionBannerItem(null, null)
            var collectionBottomItem = CollectionBottomItem(null, null)
            var videoItem = VideoItem(null, null)
            for (i in it.indices) {
                when (it[i]) {
                    is SearchItem -> searchItem = genericCastOrNull(it[i])
                    is CategoryItem -> categoryItem = genericCastOrNull(it[i])
                    is CollectionBannerItem -> collectionBannerItem = genericCastOrNull(it[i])
                    is CollectionBottomItem -> collectionBottomItem = genericCastOrNull(it[i])
                    is VideoItem -> videoItem = genericCastOrNull(it[i])
                }
            }

            if (searchItem.idViewModel != null) {
                newList.add(DividerItem("DividerItem-{0}"))
                newList.add(searchItem)
                newList.add(DividerItem("DividerItem-{0}"))
            }

            if (categoryItem.idViewModel != null) {
                newList.add(categoryItem)
                newList.add(DividerItem("DividerItem-{1}"))
            }

            if (collectionBannerItem.idViewModel != null) {
                newList.add(collectionBannerItem)
                newList.add(DividerItem("DividerItem-{2}"))
            }

            if (collectionBottomItem.idViewModel != null) {
                newList.add(TitleSeeAllItem("Collections"))
                newList.add(DividerItem("DividerItem-{3}"))
                newList.add(collectionBottomItem)
                newList.add(DividerItem("DividerItem-{4}"))
            }

            if (videoItem.idViewModel != null) {
                newList.add(TitleSeeAllItem("Videos"))
                newList.add(DividerItem("DividerItem-{5}"))
                newList.add(videoItem)
                newList.add(DividerItem("DividerItem-{6}"))
            }
            return@map newList
        }
    }

    fun fetchData2(): MediatorLiveData<MergedData> {
//        var config = PagedList.Config.Builder()
//            .setPageSize(20)
//            .setInitialLoadSizeHint(40)
//            .setEnablePlaceholders(true)
//            .build()
        val liveDataMerger = MediatorLiveData<MergedData>()

        liveDataMerger.addSource(
            LivePagedListBuilder(
                categoriesDao.liveDataFactory(),
                10
            ).build()
        ) {
            liveDataMerger.value = CategoryData(it!!)
        }
        liveDataMerger.addSource(
            LivePagedListBuilder(
                collectionDao.liveDataFactoryFromType(Constants.TYPE_HOME_BANNER),
                10
            ).build()
        ) {
            liveDataMerger.value = CollectionBannerData(it!!)
        }
        liveDataMerger.addSource(
            LivePagedListBuilder(
                collectionDao.liveDataFactoryFromType(Constants.TYPE_HOME_BOTTOM),
                10
            ).build()
        ) {
            liveDataMerger.value = CollectionBottomData(it!!)
        }
        liveDataMerger.addSource(
            LivePagedListBuilder(
                videosDao.liveDataFactoryFromType(Constants.TYPE_HOME),
                10
            ).build()
        ) {
            liveDataMerger.value = VideoData(it!!)
        }
        return liveDataMerger
    }

    fun getRepoHome(): Flowable<Resource<ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>>> {
        return createResource(
            avgleService.getCategories(),
            avgleService.getCollections((1..10).random(), (5..10).random()),
            avgleService.getCollections(1, 10),
            avgleService.getAllVideos((0..10).random()),
            newPlainResponseFourConsumer {
                execute {
                    categoriesDao.deleteAll()
                    collectionDao.deleteAll()
                    videosDao.deleteType(Constants.TYPE_HOME)

                    categoriesDao.insertIgnore(it.t1?.response?.categories!!)
                    categoriesDao.updateIgnore(it.t1?.response?.categories!!)

                    val collectionsHome = it.t2?.response?.collections!!
                    for (x in 0 until collectionsHome.size) {
                        collectionsHome[x].type = Constants.TYPE_HOME_BANNER
                    }

                    collectionDao.insertIgnore(collectionsHome)
                    collectionDao.updateIgnore(collectionsHome)

                    val collectionsHomeBottom = it.t3?.response?.collections!!
                    for (x in 0 until collectionsHome.size) {
                        collectionsHomeBottom[x].type = Constants.TYPE_HOME_BOTTOM
                    }
                    collectionDao.insertIgnore(collectionsHomeBottom)
                    collectionDao.updateIgnore(collectionsHomeBottom)

                    val videos = it.t4?.response?.videos!!
                    for (x in 0 until videos.size) {
                        videos[x].type = Constants.TYPE_HOME
                    }
                    videosDao.insertIgnore(videos)
                    videosDao.updateIgnore(videos)
                }
            }
            , newPlainConsumer {})
    }
}