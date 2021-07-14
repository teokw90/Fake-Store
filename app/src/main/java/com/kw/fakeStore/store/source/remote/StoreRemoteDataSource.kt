package com.kw.fakeStore.store.source.remote

import com.kw.fakeStore.store.source.StoreDataSource
import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.source.remote.parameter.GetListOfProductBasedOnCartIdResponseParameter
import com.kw.fakeStore.store.source.remote.parameter.UpdateListOfProductInCartResponseParameter
import com.kw.fakeStore.utils.CustomisedResult
import com.kw.fakeStore.utils.SafeApiExecutor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRemoteDataSource
@Inject constructor(private val webService: StoreWebService): StoreDataSource {
    override suspend fun getListOfProduct(): CustomisedResult<List<Product>> {
        val response = SafeApiExecutor.execute {
            webService.getListOfProduct()
        }

        return when(response) {
            is CustomisedResult.Success -> CustomisedResult.Success(data = response.data.body()!!)
            else -> CustomisedResult.Error((response as CustomisedResult.Error).errorCode, response.errorMessage)
        }
    }

    override suspend fun getListOfProductInCartBasedOn(cartId: String): CustomisedResult<GetListOfProductBasedOnCartIdResponseParameter> {
        val response = SafeApiExecutor.execute {
            webService.getListOfProductInCartBasedOn(cartId = cartId)
        }

        return when(response) {
            is CustomisedResult.Success -> CustomisedResult.Success(data = response.data)
            else -> CustomisedResult.Error((response as CustomisedResult.Error).errorCode, response.errorMessage)
        }
    }

    override suspend fun updateListOfProductInCartBasedOn(cartId: String, requestParameter: UpdateListOfProductInCartResponseParameter): CustomisedResult<GetListOfProductBasedOnCartIdResponseParameter> {
        val response = SafeApiExecutor.execute {
            webService.updateCart(cartId, requestParameter)
        }

        return when(response) {
            is CustomisedResult.Success -> CustomisedResult.Success(data = response.data)
            else -> CustomisedResult.Error((response as CustomisedResult.Error).errorCode, response.errorMessage)
        }
    }
}