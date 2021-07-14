package com.kw.fakeStore.store.di

import com.kw.fakeStore.store.source.StoreRepository
import com.kw.fakeStore.store.source.remote.DefaultStoreRepository
import com.kw.fakeStore.store.source.remote.StoreRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideStoreRepositoryWith(remoteDataSource: StoreRemoteDataSource): StoreRepository
        = DefaultStoreRepository(remoteDataSource = remoteDataSource)
}