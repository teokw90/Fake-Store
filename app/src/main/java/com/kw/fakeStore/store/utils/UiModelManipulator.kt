package com.kw.fakeStore.store.utils

import com.kw.fakeStore.store.source.entity.CartItem
import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart

object UiModelManipulator {
    fun manipulatedAndReturnListOfProductWithQuantityInCart(listOfProduct: List<Product>, listOfProductInCart: List<CartItem>): List<ProductWithQuantityInCart> =
        arrayListOf<ProductWithQuantityInCart>().run {
            listOfProduct.forEach { product ->
                val productInCart = listOfProductInCart.firstOrNull { it.productId == product.id }

                this.add(ProductWithQuantityInCart(
                    id = product.id,
                    title = product.title,
                    price = product.price,
                    category = product.category,
                    description = product.description,
                    image = product.image,
                    qualityInCart = productInCart?.quantity ?: 0
                ))
            }
            this.toList()
        }

    fun manipulatedAndReturnListOfProductInTheCartWithProductInformation(listOfProduct: List<Product>, listOfProductInCart: List<CartItem>): List<ProductWithQuantityInCart> =
        arrayListOf<ProductWithQuantityInCart>().run {
            listOfProductInCart.forEach { cartItem ->
                val product = listOfProduct.firstOrNull { it.id == cartItem.productId }
                product?.let {
                    this.add(ProductWithQuantityInCart(
                        id = it.id,
                        title = it.title,
                        price = it.price,
                        category = it.category,
                        description = it.description,
                        image = it.image,
                        qualityInCart = cartItem.quantity ?: 0
                    ))
                }
            }
            this.toList()
        }
}