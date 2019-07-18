package com.lyhoangvinh.simple.ui.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.ui.base.fragment.BaseFragment

@Suppress("UNCHECKED_CAST")
abstract class BaseSingleFragmentActivity<T : BaseFragment<*>> : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            addFragment(getContainerId(), createFragment(), null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getFragment()?.onActivityResult(requestCode, resultCode, data)
    }

    @IdRes
    protected fun getContainerId() = R.id.container

    override fun getLayoutResource() = R.layout.container

    protected abstract fun createFragment(): T

    @Nullable
    fun getFragment(): T? {
        // noinspection unchecked
        return supportFragmentManager.findFragmentById(getContainerId()) as T
    }

    override fun onBackPressed() {
        val fragment = getFragment()
        if (fragment != null) {
            if (!fragment.onBackPressed()) {
                finishWithAnimation()
            }
        }
    }
}