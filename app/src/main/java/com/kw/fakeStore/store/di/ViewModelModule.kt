package com.kw.fakeStore.store.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kw.fakeStore.di.annotation.ViewModelKey
import com.kw.fakeStore.store.viewModel.StoreViewModel
import com.kw.fakeStore.store.viewModel.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    abstract fun bindStoreViewModel(storeViewModel: StoreViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}