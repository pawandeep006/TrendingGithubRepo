package com.pawan.trendinggithubrepo

import com.pawan.trendinggithubrepo.di.component.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestBaseApp : DaggerApplication() {
    private val applicationInjector = DaggerTestAppComponent
            .builder()
            .application(this)
            .build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector
}