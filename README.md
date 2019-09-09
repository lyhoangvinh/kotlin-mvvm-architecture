<img src="https://user-images.githubusercontent.com/29915177/58609187-e9460780-82d0-11e9-943b-73d71d4f9de2.jpeg"/> 

# Kotlin MVVM Clean Architecture
This repository contains a detailed sample app that implements MVVM architecture in Kotlin using Dagger2, Room, RxJava2...

# MVVM Design Pattern

The main players in the MVVM pattern are:

* The View — that informs the ViewModel about the user’s actions

* The ViewModel — exposes streams of data relevant to the View

* The DataModel — abstracts the data source. The ViewModel works with the DataModel to get and save the data

 The MVVM pattern supports two-way data binding between the View and ViewModel and there is a many-to-one relationship between View and ViewModel.

<img src="https://user-images.githubusercontent.com/29915177/61202806-c487e100-a712-11e9-84e7-2e4c20b0f3e2.png"/>

# Libraries  

* [Kotlin](https://kotlinlang.org/)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* Android Support Libraries
* [Paging](https://developer.android.com/jetpack/androidx/releases/paging)
* [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Dagger 2 (2.11)](https://github.com/google/dagger)
* [Picasso](https://square.github.io/picasso/)
* [Retrofit](http://square.github.io/retrofit/)
* [OkHttp](http://square.github.io/okhttp/)
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)

# Dependency Injection: Dagger2

In order to reduce boilerplate and the steps required to create new Activities and Fragments, Dagger provides some base classes called DaggerApplication, DaggerAppCompatActivity and DaggerFragment.

These contain applicationInjector, AndroidInjection and AndroidSupportInjection respectively which were supposed to be included in all classes and fragments.

From the Project Pane, we have the following classes:

#### MyApplication class

A class which extends the DaggerApplication class. 

This is a base class in android that contains all android components such as activities, services, broadcast receivers etc.

```kotlin
class MyApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
```

 #### AppComponent class

This class combines all the module classes used in the app for compilation by the Dagger 2 library.
```kotlin
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DataModule::class,
        BuildersModule::class]
)
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}
```

#### ActivityBuilder class
This class houses all Activities and Fragment Modules.
```kotlin
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [TestActivityModule::class])
    abstract fun testActivity(): TestActivity

    @ContributesAndroidInjector(modules = [TestFragmentModule::class])
    abstract fun testFragment(): TestFragment
}
```

...

#### Next setting up Base Activity, Base Fragment, BaseViewModel

###### BaseActivity

  An abstract class created which extends the DaggerAppCompatActivity class. Per adventure you have some other methods that would be used in all activities, you could put them in this class while the activities extend it.
```kotlin
abstract class BaseActivity : DaggerAppCompatActivity()
```

###### BaseFragment
  An abstract class which extends the DaggerFragment class. Including methods as explained in BaseActivity also applies here.
```kotlin
abstract class BaseFragment<B : ViewDataBinding> : DaggerFragment()
```

###### BaseViewModel
An abstract class which extends the ViewModel class
```kotlin
abstract class BaseViewModel : ViewModel()
```

# Repository

<img src="https://user-images.githubusercontent.com/29915177/58611991-38913580-82db-11e9-80ce-3b289efabc83.png"/> 

Why the Repository Pattern ?

 - Decouples the application from the data sources

 - Provides data from multiple sources (DB, API) without clients being concerned about this

 - Isolates the data layer

 - Single place, centralized, consistent access to data

 - Testable business logic via Unit Tests

 - Easily add new sources
 
 So our repository now talks to the API data source and with the cache data source. We would now want to add another source for our data, a database source.
 
 On Android, we have several options here :

- using pure SQLite (too much boilerplate)

- Realm ( too complex for our use case, we don’t need most of it’s features)

- GreenDao ( a good ORM, but I think they will focus the development on objectbox in the future)

- Room ( the newly introduced ORM from Google, good support for RXJava 2 )

 I will be using for my example Room, the new library introduced by Google.

 Thread-safe live data to resolve this issue: when perform not in main Thread. (almost case is in testing)
```kotlin
class SafeMutableLiveData<T> : MutableLiveData<T>() {
    override fun setValue(value: T) {
        try {
            super.setValue(value)
        } catch (e: Exception) {
            // if we can't set value due to not in main thread, must call post value instead
            super.postValue(value)
        }
    }
}
```
#### Execute room
```kotlin
    fun execute(action: () -> Unit) {
        Completable.fromAction {
            action.invoke()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
```

### Contributing to Android Kotlin MVVM Architecture
All pull requests are welcome, make sure to follow the [contribution guidelines](CONTRIBUTING.md)
when you submit pull request.

License
=======

    Copyright 2018 LyHoangVinh.

Licensed under the the [GPL-3.0](https://www.gnu.org/licenses/gpl.html) license.
    
See the [LICENSE](https://github.com/lyhoangvinh/kotlin-mvvm-architecture/blob/master/LICENSE.md) file for the whole license text.

