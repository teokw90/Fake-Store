package com.kw.fakeStore

import android.app.Application
import android.content.Context
import com.kw.fakeStore.di.injector.ApplicationInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainApplication:  Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    init {
        instance = this
    }

    companion object {
        var instance: MainApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationInjector.init(this@MainApplication)
    }

    fun appContext(): Context {
        return applicationContext
    }
}