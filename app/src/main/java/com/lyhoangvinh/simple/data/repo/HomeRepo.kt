package com.lyhoangvinh.simple.data.repo

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.CategoriesDao
import com.lyhoangvinh.simple.data.dao.CollectionDao
import com.lyhoangvinh.simple.data.dao.VideosDao
import com.lyhoangvinh.simple.data.entinies.avgle.*
import com.lyhoangvinh.simple.data.itemviewmodel.CategoryItem
import com.lyhoangvinh.simple.data.itemviewmodel.CollectionBannerItem
import com.lyhoangvinh.simple.data.itemviewmodel.CollectionBottomItem
import com.lyhoangvinh.simple.data.itemviewmodel.VideoItem
import com.lyhoangvinh.simple.data.source.PlainResponseZipFourConsumer
import com.lyhoangvinh.simple.data.source.Resource
import com.lyhoangvinh.simple.data.response.*
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
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

        liveDataMerger.addSource(LivePagedListBuilder(categoriesDao.liveDataFactory(), 10).build()) {
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
        return Transformations.switchMap(liveDataMerger, Function {
            val pagedList = ArrayList<ItemViewModel>()
            val liveData = SafeMutableLiveData<List<ItemViewModel>>()
            when (it) {
                is CategoryData -> pagedList.add(CategoryItem(it.categoryItems))
                is CollectionBannerData -> pagedList.add(CollectionBannerItem(it.collectionBannerItems))
                is CollectionBottomData -> pagedList.add(CollectionBottomItem(it.collectionBottomItems))
                is VideoData -> pagedList.add(VideoItem(it.videoItems))
            }
            liveData.setValue(pagedList)
            return@Function liveData
        })
    }

    fun fetchData2():  MediatorLiveData<MergedData> {
        var config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(40)
            .setEnablePlaceholders(true)
            .build()
        val liveDataMerger = MediatorLiveData<MergedData>()

        liveDataMerger.addSource(LivePagedListBuilder(categoriesDao.liveDataFactory(), 10).build()) {
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
            avgleService.getCollections((1..10).random(), (10..20).random()),
            avgleService.getCollections(0, 20),
            avgleService.getAllVideos((0..20).random()),
            object :
                PlainResponseZipFourConsumer<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>> {
                override fun accept(dto: ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>) {
                    execute {
                        categoriesDao.deleteAll()
                        collectionDao.deleteAll()
                        videosDao.deleteType(Constants.TYPE_HOME)

                        categoriesDao.insertIgnore(dto.t1?.response?.categories!!)
                        categoriesDao.updateIgnore(dto.t1?.response?.categories!!)

                        val collectionsHome = dto.t2?.response?.collections!!
                        for (x in 0 until collectionsHome.size) {
                            collectionsHome[x].type = Constants.TYPE_HOME_BANNER
                        }
                        collectionDao.insertIgnore(collectionsHome)
                        collectionDao.updateIgnore(collectionsHome)

                        val collectionsHomeBottom = dto.t3?.response?.collections!!
                        for (x in 0 until collectionsHome.size) {
                            collectionsHomeBottom[x].type = Constants.TYPE_HOME_BOTTOM
                        }
                        collectionDao.insertIgnore(collectionsHomeBottom)
                        collectionDao.updateIgnore(collectionsHomeBottom)

                        val videos = dto.t4?.response?.videos!!
                        for (x in 0 until videos.size) {
                            videos[x].type = Constants.TYPE_HOME
                        }
                        videosDao.insertIgnore(videos)
                        videosDao.updateIgnore(videos)
                    }
                }
            })
    }
}