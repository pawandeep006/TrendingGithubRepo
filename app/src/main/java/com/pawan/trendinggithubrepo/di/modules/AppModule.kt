package com.pawan.trendinggithubrepo.di.modules

import com.pawan.trendinggithubrepo.source.network.ApiService
import com.pawan.trendinggithubrepo.utils.BASE_URL
import com.pawan.trendinggithubrepo.utils.CONNECT_TIMEOUT
import com.pawan.trendinggithubrepo.utils.READ_TIMEOUT
import com.pawan.trendinggithubrepo.utils.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }

}