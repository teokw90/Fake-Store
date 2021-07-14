package com.kw.fakeStore.store.useCase

import com.kw.fakeStore.BuildConfig
import com.kw.fakeStore.store.source.StoreRepository
import com.kw.fakeStore.store.source.entity.CartItem
import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.source.remote.parameter.UpdateListOfProductInCartResponseParameter
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart
import com.kw.fakeStore.store.utils.UiModelManipulator.manipulatedAndReturnListOfProductInTheCartWithProductInformation
import com.kw.fakeStore.store.utils.UiModelManipulator.manipulatedAndReturnListOfProductWithQuantityInCart
import com.kw.fakeStore.utils.CustomisedResult

interface StoreUseCase {
    suspend fun getListOfProduct(): CustomisedResult<List<ProductWithQuantityInCart>>

    suspend fun getListOfProductInCart(): CustomisedResult<List<ProductWithQuantityInCart>>

    suspend fun updateAndCombineListProductWithQuantityInCartAndWith(updatedProductWithQuantityInCart: ProductWithQuantityInCart): CustomisedResult<Boolean>

    suspend fun updateWith(updatedListOfProductWithQuantityInCart: List<ProductWithQuantityInCart>): CustomisedResult<List<ProductWithQuantityInCart>>
}

class DefaultStoreUseCase constructor(private val storeRepository: StoreRepository): StoreUseCase {
    private var listOfProduct: List<Product> = emptyList()
    private var listOfProductInCart: List<CartItem> = emptyList()
    private var userId: String? = null
    private var date: String? = null

    override suspend fun getListOfProduct(): CustomisedResult<List<ProductWithQuantityInCart>> {
        if (listOfProductInCart.isEmpty()) {
            when(val result = storeRepository.getListOfProductInCartBasedOn(cartId = BuildConfig.CartId)) {
                is CustomisedResult.Success -> with(result) {
                    userId = data.userId
                    date = data.date
                    listOfProductInCart = data.listOfProducts
                }
                else -> listOfProductInCart = emptyList()
            }
        }

         when(val result = storeRepository.getListOfProduct()) {
            is CustomisedResult.Success -> listOfProduct = result.data
            else -> return (result as CustomisedResult.Error)
        }

        return CustomisedResult.Success(data = manipulatedAndReturnListOfProductWithQuantityInCart(listOfProduct, listOfProductInCart))
    }

    override suspend fun getListOfProductInCart(): CustomisedResult<List<ProductWithQuantityInCart>> {
        if (listOfProduct.isEmpty()) {
            when (val result = storeRepository.getListOfProduct()) {
                is CustomisedResult.Success -> listOfProduct = result.data
                else -> return (result as CustomisedResult.Error)
            }
        }

        when(val result = storeRepository.getListOfProductInCartBasedOn(cartId = BuildConfig.CartId)) {
            is CustomisedResult.Success -> with(result) {
                userId = data.userId
                date = data.date
                listOfProductInCart = data.listOfProducts
            }
            else -> return CustomisedResult.Success(data = emptyList())
        }

        return CustomisedResult.Success(data = manipulatedAndReturnListOfProductInTheCartWithProductInformation(listOfProduct, listOfProductInCart))
    }

    override suspend fun updateAndCombineListProductWithQuantityInCartAndWith(updatedProductWithQuantityInCart: ProductWithQuantityInCart): CustomisedResult<Boolean> {
        if (listOfProductInCart.isEmpty()) {
            when (val result = storeRepository.getListOfProductInCartBasedOn(cartId = BuildConfig.CartId)) {
                is CustomisedResult.Success ->  with(result) {
                    userId = data.userId
                    date = data.date
                    listOfProductInCart = data.listOfProducts
                }
                else -> listOfProductInCart = emptyList()
            }
        }

        val requestParameter = UpdateListOfProductInCartResponseParameter(
            userId = userId!!,
            date = date!!,
            listOfProducts = arrayListOf<CartItem>().apply {
                listOfProductInCart.forEach { cartItem ->
                    add(CartItem(
                        productId = cartItem.productId,
                        quantity = if (cartItem.productId == updatedProductWithQuantityInCart.id) {
                            updatedProductWithQuantityInCart.qualityInCart
                        } else {
                            cartItem.quantity
                        }
                    ))
                }
            }.toList())

        return when(val result = storeRepository.updateListOfProductInCartBasedOn(cartId = BuildConfig.CartId, requestParameter = requestParameter)) {
            is CustomisedResult.Success -> with(result) {
                userId = data.userId
                date = data.date
                listOfProductInCart = data.listOfProducts

                CustomisedResult.Success(data = true)
            }
            else -> return (result as CustomisedResult.Error)
        }
    }

    override suspend fun updateWith(updatedListOfProductWithQuantityInCart: List<ProductWithQuantityInCart>): CustomisedResult<List<ProductWithQuantityInCart>> {
        val requestParameter = UpdateListOfProductInCartResponseParameter(
            userId = userId!!,
            date = date!!,
            listOfProducts = arrayListOf<CartItem>().apply {
                updatedListOfProductWithQuantityInCart.forEach { productWithQuantityInCart ->
                    add(CartItem(
                        productId = productWithQuantityInCart.id,
                        quantity = productWithQuantityInCart.qualityInCart))
                }
            }.toList())

        when(val result = storeRepository.updateListOfProductInCartBasedOn(cartId = BuildConfig.CartId, requestParameter = requestParameter)) {
            is CustomisedResult.Success -> with(result) {
                userId = data.userId
                date = data.date
                listOfProductInCart = data.listOfProducts
            }
            else -> return (result as CustomisedResult.Error)
        }

        if (listOfProduct.isEmpty()) {
            when (val result = storeRepository.getListOfProduct()) {
                is CustomisedResult.Success -> listOfProduct = result.data
                else -> return (result as CustomisedResult.Error)
            }
        }

        return CustomisedResult.Success(data = manipulatedAndReturnListOfProductInTheCartWithProductInformation(listOfProduct, listOfProductInCart))
    }
}