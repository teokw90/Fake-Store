package com.kw.fakeStore.store.source.remote

import com.kw.fakeStore.store.source.StoreDataSource
import com.kw.fakeStore.store.source.StoreRepository
import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.source.remote.parameter.GetListOfProductBasedOnCartIdResponseParameter
import com.kw.fakeStore.store.source.remote.parameter.UpdateListOfProductInCartResponseParameter
import com.kw.fakeStore.utils.CustomisedResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultStoreRepository
@Inject constructor(private val remoteDataSource: StoreDataSource): StoreRepository {
    override suspend fun getListOfProduct(): CustomisedResult<List<Product>>
        = remoteDataSource.getListOfProduct()

    override suspend fun getListOfProductInCartBasedOn(cartId: String): CustomisedResult<GetListOfProductBasedOnCartIdResponseParameter>
        = remoteDataSource.getListOfProductInCartBasedOn(cartId = cartId)

    override suspend fun updateListOfProductInCartBasedOn(cartId: String, requestParameter: UpdateListOfProductInCartResponseParameter): CustomisedResult<GetListOfProductBasedOnCartIdResponseParameter>
        = remoteDataSource.updateListOfProductInCartBasedOn(cartId = cartId, requestParameter = requestParameter)
}