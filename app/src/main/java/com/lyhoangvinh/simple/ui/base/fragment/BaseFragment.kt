package com.lyhoangvinh.simple.ui.base.fragment

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.utils.AutoClearedValue
import com.lyhoangvinh.simple.utils.hideKeyboard


/**
 * Created by lyhoangvinh on 04/01/18.
 * Base class using data binding. The binding object reference will be removed as soon as the fragment view is destroyed
 */

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    private var dialog: Dialog? = null

    lateinit var binding: B

    abstract fun getLayoutResource(): Int

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AutoClearedValue(this, DataBindingUtil.inflate<B>(inflater, getLayoutResource(), container, false)).get()!!
        return binding.root
    }

    protected fun getFragmentArguments(): Bundle? {
        return arguments
    }

    private fun showLoadingDialog(ctx: Context): Dialog {
        Dialog(ctx).let {
            it.show()
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setContentView(R.layout.progress_dialog)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }

    fun finishFragment() {
        hideKeyboard()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.finishAfterTransition()
        } else {
            activity?.finish()
        }
    }

    /**
     * @return true if fragment should handle back press, false if not (activity will handle back press event)
     */
    open fun onBackPressed(): Boolean {
        return false
    }
}