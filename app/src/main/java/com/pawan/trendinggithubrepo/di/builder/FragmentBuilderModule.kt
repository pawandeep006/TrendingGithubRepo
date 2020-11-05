package com.pawan.trendinggithubrepo.di.builder

import com.pawan.trendinggithubrepo.view.fragment.RepoDetailsFragment
import com.pawan.trendinggithubrepo.view.fragment.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoListFragment(): RepoListFragment


    @ContributesAndroidInjector
    abstract fun contributeRepoDetailsFragment(): RepoDetailsFragment
}