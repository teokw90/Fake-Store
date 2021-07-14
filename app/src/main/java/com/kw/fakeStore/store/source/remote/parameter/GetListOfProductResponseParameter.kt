package com.kw.fakeStore.store.source.remote.parameter

import com.google.gson.annotations.SerializedName
import com.kw.fakeStore.store.source.entity.CartItem

data class GetListOfProductBasedOnCartIdResponseParameter(
    val id: String,
    val userId: String,
    val date: String,
    @SerializedName("products") val listOfProducts: List<CartItem>
)