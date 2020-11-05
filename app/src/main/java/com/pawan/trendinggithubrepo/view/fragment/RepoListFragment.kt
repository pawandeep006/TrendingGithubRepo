package com.pawan.trendinggithubrepo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawan.trendinggithubrepo.adapter.RepoListAdapter
import com.pawan.trendinggithubrepo.databinding.FragmentRepoListBinding
import com.pawan.trendinggithubrepo.source.network.model.Item
import com.pawan.trendinggithubrepo.utils.MyIdlingResource
import com.pawan.trendinggithubrepo.utils.alert
import com.pawan.trendinggithubrepo.view.activity.MainActivity
import com.pawan.trendinggithubrepo.viewmodel.SharedViewModel

class RepoListFragment : BaseFragment<SharedViewModel>() {

    private lateinit var adapter: RepoListAdapter
    private lateinit var binding: FragmentRepoListBinding

    companion object {
        @JvmStatic
        fun newInstance() =
            RepoListFragment()
    }

    override fun getViewModel(): Class<SharedViewModel> = SharedViewModel::class.java

    override fun getTitle(): String = "Trending Github Repos"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        binding.isLoading = true
        MyIdlingResource.increment();
        viewModel?.repoListLiveData?.observe(viewLifecycleOwner, Observer {
            binding.isLoading = false
            adapter.setData(it)
            MyIdlingResource.decrement();
        })

        viewModel?.errorLiveData?.observe(viewLifecycleOwner, Observer {
            binding.isLoading = false
            it?.alert(context)
            MyIdlingResource.decrement();
        })
    }

    private fun setupList() {
        adapter = RepoListAdapter() {
            viewModel?.selectedItem = it as Item
            (activity as MainActivity).loadRepoDetailsFragment()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }
}