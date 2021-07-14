package com.kw.fakeStore.store.useCase

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import com.kw.fakeStore.store.mock.MockDataHelper
import com.kw.fakeStore.store.utils.UiModelManipulator
import org.junit.Test

class StoreUseCaseTest {
    private val listOfProduct by lazy {
        MockDataHelper.getListOfProduct()
    }

    private val listOfProductInCart by lazy {
        MockDataHelper.getListOfProductInCart()
    }

    @Test
    fun getListOfProduct_returnListOfProductWithQuantityInCart() {
        // WHEN - manipulate it
        val listOfProductWithQuantityInCart = UiModelManipulator.manipulatedAndReturnListOfProductWithQuantityInCart(
            listOfProduct = listOfProduct,
            listOfProductInCart = listOfProductInCart)

        // THEN - verify result
        assertThat(listOfProductWithQuantityInCart.size, `is`(1))

        val productWithQuantityInCart = listOfProductWithQuantityInCart[0]
        val product = listOfProduct[0]
        val cartItem = listOfProductInCart[0]
        assertThat(productWithQuantityInCart.id, `is`(product.id))
        assertThat(productWithQuantityInCart.title, `is`(product.title))
        assertThat(productWithQuantityInCart.price, `is`(product.price))
        assertThat(productWithQuantityInCart.category, `is`(product.category))
        assertThat(productWithQuantityInCart.description, `is`(product.description))
        assertThat(productWithQuantityInCart.image, `is`(product.image))
        assertThat(productWithQuantityInCart.qualityInCart, `is`(cartItem.quantity))
    }

    @Test
    fun getListOfProductInCart_returnListOfProductWithQuantityInCart() {
        // WHEN - manipulate it
        val listOfProductWithQuantityInCart = UiModelManipulator.manipulatedAndReturnListOfProductInTheCartWithProductInformation(
            listOfProduct = listOfProduct,
            listOfProductInCart = listOfProductInCart)

        // THEN - verify result
        assertThat(listOfProductWithQuantityInCart.size, `is`(1))

        val productWithQuantityInCart = listOfProductWithQuantityInCart[0]
        val product = listOfProduct[0]
        val cartItem = listOfProductInCart[0]
        assertThat(productWithQuantityInCart.id, `is`(product.id))
        assertThat(productWithQuantityInCart.title, `is`(product.title))
        assertThat(productWithQuantityInCart.price, `is`(product.price))
        assertThat(productWithQuantityInCart.category, `is`(product.category))
        assertThat(productWithQuantityInCart.description, `is`(product.description))
        assertThat(productWithQuantityInCart.image, `is`(product.image))
        assertThat(productWithQuantityInCart.qualityInCart, `is`(cartItem.quantity))
    }

    @Test
    fun updateWithUpdatedListOfProductWithQuantityInCart_returnListOfProductWithQuantityInCart() {
        // WHEN - manipulate it
        val listOfProductWithQuantityInCart = UiModelManipulator.manipulatedAndReturnListOfProductInTheCartWithProductInformation(
            listOfProduct = listOfProduct,
            listOfProductInCart = listOfProductInCart)

        // THEN - verify result
        assertThat(listOfProductWithQuantityInCart.size, `is`(1))

        val productWithQuantityInCart = listOfProductWithQuantityInCart[0]
        val product = listOfProduct[0]
        val cartItem = listOfProductWithQuantityInCart[0]
        assertThat(productWithQuantityInCart.id, `is`(product.id))
        assertThat(productWithQuantityInCart.title, `is`(product.title))
        assertThat(productWithQuantityInCart.price, `is`(product.price))
        assertThat(productWithQuantityInCart.category, `is`(product.category))
        assertThat(productWithQuantityInCart.description, `is`(product.description))
        assertThat(productWithQuantityInCart.image, `is`(product.image))
        assertThat(productWithQuantityInCart.qualityInCart, `is`(cartItem.qualityInCart))
    }
}