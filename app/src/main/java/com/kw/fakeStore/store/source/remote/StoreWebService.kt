package com.kw.fakeStore.store.source.remote

import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.source.remote.parameter.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface StoreWebService {
    @GET("products")
    suspend fun getListOfProduct(): Response<List<Product>>

    @GET("carts/{id}")
    suspend fun getListOfProductInCartBasedOn(@Path("id") cartId: String): GetListOfProductBasedOnCartIdResponseParameter

    @PUT("carts/{id}")
    suspend fun updateCart(@Path("id") cartId: String, @Body requestParameter: UpdateListOfProductInCartResponseParameter): GetListOfProductBasedOnCartIdResponseParameter

}