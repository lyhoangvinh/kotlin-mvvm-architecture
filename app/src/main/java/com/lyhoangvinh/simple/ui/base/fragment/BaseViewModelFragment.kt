package com.lyhoangvinh.simple.ui.base.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import com.lyhoangvinh.simple.data.source.State
import com.lyhoangvinh.simple.data.source.Status
import com.lyhoangvinh.simple.di.Injectable
import com.lyhoangvinh.simple.ui.base.activity.BaseActivity
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Base fragment class that has a ViewModel extending [BaseViewModel].
 * All fragments extend this fragment must be added into [BaseActivity]
 *
 * Progress showing and message showing will be handled automatically when viewModel's state changed
 * through [BaseViewModel.stateLiveData]
 */

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : BaseViewModel> : BaseFragment<B>(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //    @Inject
    lateinit var viewModel: VM

    protected abstract fun createViewModelClass(): Class<VM>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity !is BaseActivity) {
            throw IllegalStateException("All fragment's container must extend BaseActivity")
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(createViewModelClass())
        viewModel.onCreate(this, getFragmentArguments())
        viewModel.stateLiveData.observe(this, Observer { handleState(it) })
        initialize(view, activity)
    }

    protected abstract fun initialize(view: View, ctx: Context?)

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    private fun handleState(state: State?) {
        setLoading(state != null && state.status == Status.LOADING)
    }

    protected open fun setLoading(loading: Boolean) {
        (activity as BaseActivity).setLoading(loading)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }
}