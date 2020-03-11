package com.lyhoangvinh.simple.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
/**
 * ViewModelFactory which uses Dagger to create the instances.
 */
//@Singleton
//class ViewModelFactory @Inject constructor(private val creators: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        var creator: Provider<out ViewModel>? = creators[modelClass]
//        if (creator == null) {
//            for (entry in creators.entries) {
//                if (modelClass.isAssignableFrom(entry.key)) {
//                    creator = entry.value
//                    break
//                }
//            }
//        }
//        if (creator == null) {
//            throw IllegalArgumentException("unknown model class $modelClass")
//        }
//        try {
//            return creator.get() as T
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
//
//    }
////    override fun <T : ViewModel> create(modelClass: Class<T>): T = creators[modelClass]?.get() as T
//}

@Singleton
class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val found = creators.entries.find { modelClass.isAssignableFrom(it.key) }
        val creator = found?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
