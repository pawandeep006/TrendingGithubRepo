package com.pawan.trendinggithubrepo.view.activity

import android.os.Bundle
import com.pawan.trendinggithubrepo.R
import com.pawan.trendinggithubrepo.view.fragment.RepoDetailsFragment
import com.pawan.trendinggithubrepo.view.fragment.RepoListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutRes: Int
        get() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadListFragment()
    }

    private fun loadListFragment() {
        replaceFragment(container, RepoListFragment.newInstance())
    }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    fun loadRepoDetailsFragment() {
        replaceFragment(container, RepoDetailsFragment.newInstance())
    }
}