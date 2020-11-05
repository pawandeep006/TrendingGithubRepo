package com.pawan.trendinggithubrepo.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<V : ViewModel?> : DaggerFragment() {

    var viewModel: V? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun getTitle(): String

    protected abstract fun getViewModel(): Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(getViewModel())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(getTitle())
    }

    fun setActionBarTitle(title: String?) {
        title?.let {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }
    }
}
