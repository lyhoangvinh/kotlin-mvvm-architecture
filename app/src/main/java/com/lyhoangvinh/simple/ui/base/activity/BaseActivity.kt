package com.lyhoangvinh.simple.ui.base.activity

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.lyhoangvinh.simple.ui.base.interfaces.UiRefreshable
import com.lyhoangvinh.simple.utils.createDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Base activity that will be injected automatically by implementing {@link HasSupportFragmentInjector}
 * SEE {@link com.lyhoangvinh.simple.di.AppInjector}
 * All fragment inside this activity is injected as well
 */

abstract class BaseActivity : DaggerAppCompatActivity() {

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!shouldUseDataBinding()) {
            // set contentView if child activity not use dataBinding
            setContentView(getLayoutResource())
        }
    }

    open fun setLoading(loading: Boolean) {
        if (loading) {
            hideProgress()
            if (dialog == null) {
                dialog = createDialog()
            }
            dialog?.show()
        } else {
            hideProgress()
        }
    }

    private fun hideProgress() {
        if (this is UiRefreshable) {
            (this as UiRefreshable).doneRefresh()
        }
        if (dialog != null && dialog!!.isShowing)
            dialog?.dismiss()
    }

    fun addFragment(@IdRes res: Int, fragment: androidx.fragment.app.Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .add(res, fragment, tag)
            .commit()
    }


    protected fun finishWithAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
    }

    /**
     * @return true if child activity should use data binding instead of [.setContentView]
     */
    protected open fun shouldUseDataBinding(): Boolean {
        return false
    }

    protected abstract fun getLayoutResource(): Int
}