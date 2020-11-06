package com.pawan.trendinggithubrepo.di.component

import com.pawan.trendinggithubrepo.TestBaseApp
import com.pawan.trendinggithubrepo.di.TestAppModule
import com.pawan.trendinggithubrepo.di.builder.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            TestAppModule::class,
            ActivityBuilderModule::class
        ]
)
interface TestAppComponent : AndroidInjector<TestBaseApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TestBaseApp): Builder

        fun build(): TestAppComponent
    }

}
