package com.kw.fakeStore.store.di

import dagger.Module

@Module(includes = [
    FragmentModule::class,
    RepositoryModule::class,
    UseCaseModule::class,
    ViewModelModule::class,
    WebServiceModule::class
])
abstract class StoreModule