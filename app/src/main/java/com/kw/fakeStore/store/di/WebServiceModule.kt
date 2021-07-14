package com.kw.fakeStore.store.di

import com.google.gson.GsonBuilder
import com.kw.fakeStore.BuildConfig
import com.kw.fakeStore.store.source.remote.StoreWebService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebServiceModule {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(BuildConfig.ServiceTimeout, TimeUnit.MILLISECONDS)
        .readTimeout(BuildConfig.ServiceTimeout, TimeUnit.MILLISECONDS)
        .writeTimeout(BuildConfig.ServiceTimeout, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(false)
        .build()

    private val gson = GsonBuilder().apply {
        setDateFormat(BuildConfig.DateForamt)
    }.create()

    @Provides
    @Singleton
    fun provideNoteWebService(): StoreWebService = Retrofit.Builder()
        .baseUrl("${BuildConfig.BaseURL}/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(StoreWebService::class.java)
}