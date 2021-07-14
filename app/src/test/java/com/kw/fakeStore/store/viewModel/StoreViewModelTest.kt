package com.kw.fakeStore.store.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kw.fakeStore.MainCoroutineRule
import com.kw.fakeStore.getOrAwaitValue
import com.kw.fakeStore.store.mock.MockDataHelper
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart
import com.kw.fakeStore.store.useCase.FakeStoreUseCase
import com.kw.fakeStore.store.useCase.StoreUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StoreViewModelTest {
    // Mock Data
    private val listOfProduct by lazy {
        MockDataHelper.getListOfProduct()
    }

    private val listOfProductInCart by lazy {
        MockDataHelper.getListOfProductInCart()
    }

    // Use a fake use case to be injected into viewModel
    private lateinit var storeUseCase: StoreUseCase

    // Subject under test
    private lateinit var storeViewModel: StoreViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        storeUseCase = FakeStoreUseCase()

        // Given a fresh ViewModel
        storeViewModel = StoreViewModel(storeUseCase)
    }

    @Test
    fun getListOfProduct_returnListOfProductWithQuantityInCart() {
        // WHEN - trigger viewModel's getListOfProduct
        storeViewModel.getListOfProduct()

        // THEN - empty object is returned from the fakeDiscoverWeeklySpotlightUseCase
        val response = storeViewModel.listOfProductWithQuantityInCart.getOrAwaitValue()
        val productWithQuantityInCart = response[0]
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
        // WHEN - trigger viewModel's getListOfProduct
        storeViewModel.getListOfProductInCart()

        // THEN - empty object is returned from the fakeDiscoverWeeklySpotlightUseCase
        val response = storeViewModel.listOfProductWithQuantityInCart.getOrAwaitValue()
        val productWithQuantityInCart = response[0]
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
    fun updateAndCombineListProductWithQuantityInCartAndWithUpdatedProductWithQuantityInCart_returnListOfProductWithQuantityInCart() {
        // GIVEN - update product quantity
        val updatedProductWithQuantityInCart = ProductWithQuantityInCart(
            id = 7,
            title = "White Gold Plated Princess",
            price =  "9.99",
            description = "Classic Created Wedding Engagement Solitaire Diamond Promise Ring for Her. Gifts to spoil your love more for Engagement, Wedding, Anniversary, Valentine's Day...",
            category = "jewelery",
            image = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg",
            qualityInCart = 2
        )

        // WHEN - trigger viewModel's getListOfProduct
        storeViewModel.updateAndCombineListProductWithQuantityInCartAndWith(updatedProductWithQuantityInCart)

        // THEN - empty object is returned from the fakeDiscoverWeeklySpotlightUseCase
        val response = storeViewModel.updateSuccessStatus.getOrAwaitValue()
        val cartItem = listOfProductInCart[0]
        assertThat(response, `is`(true))
    }

    @Test
    fun updateWithUpdateListOfProductWithQuantityInCart_returnListOfProductWithQuantityInCart() {
        // GIVEN - update product quantity
        storeViewModel.getListOfProduct()
        val listOfProductWithQuantityInCart = storeViewModel.listOfProductWithQuantityInCart.getOrAwaitValue()
        val listOfUpdatedProductWithQuantityInCart = listOfProductWithQuantityInCart[0].apply {
            qualityInCart += 1
        }

        // WHEN - trigger viewModel's getListOfProduct
        storeViewModel.updateWith(listOf(listOfUpdatedProductWithQuantityInCart))

        // THEN - empty object is returned from the fakeDiscoverWeeklySpotlightUseCase
        val response = storeViewModel.listOfProductWithQuantityInCart.getOrAwaitValue()
        val productWithQuantityInCart = response[0]
        val product = listOfProduct[0]
        assertThat(productWithQuantityInCart.id, `is`(product.id))
        assertThat(productWithQuantityInCart.title, `is`(product.title))
        assertThat(productWithQuantityInCart.price, `is`(product.price))
        assertThat(productWithQuantityInCart.category, `is`(product.category))
        assertThat(productWithQuantityInCart.description, `is`(product.description))
        assertThat(productWithQuantityInCart.image, `is`(product.image))
        assertThat(productWithQuantityInCart.qualityInCart, `is`(2))
    }
}