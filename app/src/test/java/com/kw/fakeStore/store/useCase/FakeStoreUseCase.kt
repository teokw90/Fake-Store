package com.kw.fakeStore.store.useCase

import com.kw.fakeStore.store.mock.MockDataHelper
import com.kw.fakeStore.store.source.entity.CartItem
import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart
import com.kw.fakeStore.store.utils.UiModelManipulator
import com.kw.fakeStore.utils.CustomisedResult

class FakeStoreUseCase: StoreUseCase {
    private var listOfProduct = MockDataHelper.getListOfProduct()
    private var listOfProductInCart = MockDataHelper.getListOfProductInCart()
    private val userId by lazy {
        MockDataHelper.getListOfProductBasedOnCartIdResponseParameter().userId
    }
    private val date: String by lazy {
        MockDataHelper.getListOfProductBasedOnCartIdResponseParameter().date
    }

    fun addListOfProduct(vararg products: Product) {
        val newListOfProduct = listOfProduct.toMutableList()

        for (item in products) {
            newListOfProduct.add(item)
        }

        listOfProduct = newListOfProduct.toList()
    }

    fun addListOfProduct(vararg cartItems: CartItem) {
        val newListOfProductInCart = listOfProductInCart.toMutableList()

        for (item in cartItems) {
            newListOfProductInCart.add(item)
        }

        listOfProductInCart = newListOfProductInCart.toList()
    }

    override suspend fun getListOfProduct(): CustomisedResult<List<ProductWithQuantityInCart>>
        = CustomisedResult.Success(data = UiModelManipulator.manipulatedAndReturnListOfProductWithQuantityInCart(
            listOfProduct,
            listOfProductInCart))

    override suspend fun getListOfProductInCart(): CustomisedResult<List<ProductWithQuantityInCart>>
        = CustomisedResult.Success(data = UiModelManipulator.manipulatedAndReturnListOfProductInTheCartWithProductInformation(
            listOfProduct,
            listOfProductInCart))

    override suspend fun updateAndCombineListProductWithQuantityInCartAndWith(
        updatedProductWithQuantityInCart: ProductWithQuantityInCart
    ): CustomisedResult<Boolean>
        = CustomisedResult.Success(data = true)

    override suspend fun updateWith(updatedListOfProductWithQuantityInCart: List<ProductWithQuantityInCart>): CustomisedResult<List<ProductWithQuantityInCart>>
        = CustomisedResult.Success(data = UiModelManipulator.manipulatedAndReturnListOfProductWithQuantityInCart(
            listOfProduct,
            listOfProductInCart))
}