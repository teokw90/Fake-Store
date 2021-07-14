package com.kw.fakeStore.store.source

import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.source.remote.parameter.GetListOfProductBasedOnCartIdResponseParameter
import com.kw.fakeStore.store.source.remote.parameter.UpdateListOfProductInCartResponseParameter
import com.kw.fakeStore.utils.CustomisedResult

interface StoreRepository {
    suspend fun getListOfProduct(): CustomisedResult<List<Product>>

    suspend fun getListOfProductInCartBasedOn(cartId: String): CustomisedResult<GetListOfProductBasedOnCartIdResponseParameter>

    suspend fun updateListOfProductInCartBasedOn(cartId: String, requestParameter: UpdateListOfProductInCartResponseParameter): CustomisedResult<GetListOfProductBasedOnCartIdResponseParameter>
}