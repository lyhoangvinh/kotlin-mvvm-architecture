package com.lyhoangvinh.simple.ui.features.test

import android.os.Bundle
import com.lyhoangvinh.simple.data.repo.IssuesRepo
import com.lyhoangvinh.simple.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val issuesRepo: IssuesRepo) : BaseViewModel() {

    fun liveData() = issuesRepo.liveData()

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        execute(true, issuesRepo.getRepoIssues(true, 1), null)
    }
}