package com.kw.fakeStore.store.utils

import com.kw.fakeStore.store.mock.MockDataHelper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UiModelManipulatorTest {
    private val listOfProduct by lazy {
        MockDataHelper.getListOfProduct()
    }

    private val listOfProductInCart by lazy {
        MockDataHelper.getListOfProductInCart()
    }

    @Test
    fun manipulatedAndReturnListOfProductWithQuantityInCart_returnListOfProductWithQuantityInCart() {
        // GIVEN - list of mock data
        // WHEN - manipulate it
        val listOfProductWithQuantityInCart = UiModelManipulator.manipulatedAndReturnListOfProductWithQuantityInCart(
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

    @Test
    fun manipulatedAndReturnListOfProductInTheCartWithProductInformation_returnListOfProductWithQuantityInCart() {
        // GIVEN - list of mock data
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