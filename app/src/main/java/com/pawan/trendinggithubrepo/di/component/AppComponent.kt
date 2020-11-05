package com.pawan.trendinggithubrepo.di.component

import android.app.Application
import com.pawan.trendinggithubrepo.BaseApp
import com.pawan.trendinggithubrepo.di.builder.ActivityBuilderModule
import com.pawan.trendinggithubrepo.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: BaseApp)
}