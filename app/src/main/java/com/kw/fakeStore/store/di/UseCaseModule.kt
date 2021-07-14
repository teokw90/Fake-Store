package com.kw.fakeStore.store.di

import com.kw.fakeStore.store.source.StoreRepository
import com.kw.fakeStore.store.useCase.DefaultStoreUseCase
import com.kw.fakeStore.store.useCase.StoreUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideStoreUseCaseWith(storeRepository: StoreRepository): StoreUseCase = DefaultStoreUseCase(storeRepository)
}