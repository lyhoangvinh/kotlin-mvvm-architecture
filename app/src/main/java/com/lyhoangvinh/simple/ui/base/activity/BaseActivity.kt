package com.lyhoangvinh.simple.ui.base.activity

import android.app.Dialog
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.lyhoangvinh.simple.utils.createDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Base activity that will be injected automatically by implementing {@link HasSupportFragmentInjector}
 * SEE {@link com.lyhoangvinh.simple.di.AppInjector}
 * All fragment inside this activity is injected as well
 */

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    private var dialog: Dialog? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    open fun setLoading(loading: Boolean) {
        if (loading) {
            hideProgress()
            if (dialog == null) {
                dialog = createDialog()
            }
            dialog?.show()
        } else {
            if (dialog != null && dialog!!.isShowing)
                dialog?.dismiss()
        }
    }

    private fun hideProgress() {
        if (dialog != null && dialog!!.isShowing)
            dialog?.dismiss()
    }
}