package com.pawan.trendinggithubrepo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pawan.trendinggithubrepo.databinding.FragmentRepoDetailBinding
import com.pawan.trendinggithubrepo.viewmodel.SharedViewModel

class RepoDetailsFragment : BaseFragment<SharedViewModel>() {

    private lateinit var binding: FragmentRepoDetailBinding

    companion object {

        @JvmStatic
        fun newInstance() =
            RepoDetailsFragment()

    }

    override fun getViewModel(): Class<SharedViewModel> = SharedViewModel::class.java

    override fun getTitle(): String = "Github Repo Detail"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.selectedItem?.let { item ->
            binding.item = item
            setActionBarTitle(item.name)
        }
    }
}