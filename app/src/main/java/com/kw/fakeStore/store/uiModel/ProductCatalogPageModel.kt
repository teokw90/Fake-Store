package com.kw.fakeStore.store.uiModel

import java.io.Serializable

data class ProductWithQuantityInCart(
    val id: Int,
    val title: String,
    val price: String,
    val category: String,
    val description: String,
    val image: String,
    var qualityInCart: Int
    ): Serializable
