package com.kw.fakeStore.store.mock

import com.kw.fakeStore.store.source.entity.CartItem
import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.source.remote.parameter.GetListOfProductBasedOnCartIdResponseParameter

object MockDataHelper {
    // region GetListOfProduct
    private val product: Product by lazy {
        Product(
            id = 7,
            title = "White Gold Plated Princess",
            price =  "9.99",
            description = "Classic Created Wedding Engagement Solitaire Diamond Promise Ring for Her. Gifts to spoil your love more for Engagement, Wedding, Anniversary, Valentine's Day...",
            category = "jewelery",
            image = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg"
        )
    }

    private val mockListOfProduct: List<Product> by lazy {
        listOf(product)
    }

    fun getListOfProduct() = mockListOfProduct
    // endRegion GetListOfProduct

    // region GetListOfProductBasedOnCartId
    private val cartItem: CartItem by lazy {
        CartItem(productId = 7, quantity = 1)
    }

    private val mockGetListOfProductBasedOnCartIdResponseParameter: GetListOfProductBasedOnCartIdResponseParameter by lazy {
        GetListOfProductBasedOnCartIdResponseParameter(
            id = "5",
            userId = "3",
            date = "2020-03-01T00:00:02.000Z",
            listOfProducts = listOf(cartItem)
        )
    }

    fun getListOfProductBasedOnCartIdResponseParameter() = mockGetListOfProductBasedOnCartIdResponseParameter

    fun getListOfProductInCart() = mockGetListOfProductBasedOnCartIdResponseParameter.listOfProducts
    // endRegion GetListOfProductBasedOnCartId
}