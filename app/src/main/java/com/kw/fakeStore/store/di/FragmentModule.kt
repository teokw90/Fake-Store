package com.kw.fakeStore.store.di

import com.kw.fakeStore.store.view.fragment.MyCartFragment
import com.kw.fakeStore.store.view.fragment.ProductDetailsFragment
import com.kw.fakeStore.store.view.fragment.ProductsCatalogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeProductsCatalogFragment(): ProductsCatalogFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeMyCartFragment(): MyCartFragment
}