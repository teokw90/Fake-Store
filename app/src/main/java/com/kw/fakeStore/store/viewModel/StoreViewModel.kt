package com.kw.fakeStore.store.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart
import com.kw.fakeStore.store.useCase.StoreUseCase
import com.kw.fakeStore.utils.CustomisedResult
import kotlinx.coroutines.*
import javax.inject.Inject

class StoreViewModel
@Inject constructor(private val storeUseCase: StoreUseCase): ViewModel() {
    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _listOfProductWithQuantityInCart = MutableLiveData<List<ProductWithQuantityInCart>>()
    val listOfProductWithQuantityInCart: LiveData<List<ProductWithQuantityInCart>>
        get() = _listOfProductWithQuantityInCart

    private val _updateSuccessStatus = MutableLiveData<Boolean>()
    val updateSuccessStatus: LiveData<Boolean>
        get() = _updateSuccessStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getListOfProduct() {
        coroutineScope.launch {
            val result: CustomisedResult<List<ProductWithQuantityInCart>> = storeUseCase.getListOfProduct()

            checkAndReturnListOfProductFrom(result)
        }
    }

    private suspend fun checkAndReturnListOfProductFrom(result: CustomisedResult<List<ProductWithQuantityInCart>>) {
        when(result) {
            is CustomisedResult.Success -> {
                withContext(Dispatchers.Main) {
                    _listOfProductWithQuantityInCart.value = result.data
                }
            }
            else -> {
                val errorResult = (result as CustomisedResult.Error)
                errorResult.errorMessage?.let { returnErrorMessage(it) }
            }
        }
    }

    private suspend fun returnErrorMessage(errorMessage: String) {
        withContext(Dispatchers.Main) {
            _errorMessage.value = errorMessage
        }
    }

    fun getListOfProductInCart() {
        coroutineScope.launch {
            val result: CustomisedResult<List<ProductWithQuantityInCart>> = storeUseCase.getListOfProductInCart()

            checkAndReturnListOfProductFrom(result)
        }
    }

    fun updateAndCombineListProductWithQuantityInCartAndWith(updatedProductWithQuantityInCart: ProductWithQuantityInCart) {
        coroutineScope.launch {
            when(val result: CustomisedResult<Boolean> = storeUseCase.updateAndCombineListProductWithQuantityInCartAndWith(updatedProductWithQuantityInCart)) {
                is CustomisedResult.Success -> {
                    withContext(Dispatchers.Main) {
                        _updateSuccessStatus.value = result.data
                    }
                }
                else -> {
                    val errorResult = (result as CustomisedResult.Error)
                    errorResult.errorMessage?.let { returnErrorMessage(it) }
                }
            }
        }
    }

    fun updateWith(updateListOfProductWithQuantityInCart: List<ProductWithQuantityInCart>) {
        coroutineScope.launch {
            val result: CustomisedResult<List<ProductWithQuantityInCart>> = storeUseCase.updateWith(updateListOfProductWithQuantityInCart)

            checkAndReturnListOfProductWithUpdateSuccessFrom(result)
        }
    }

    private suspend fun checkAndReturnListOfProductWithUpdateSuccessFrom(result: CustomisedResult<List<ProductWithQuantityInCart>>) {
        when(result) {
            is CustomisedResult.Success -> {
                withContext(Dispatchers.Main) {
                    _listOfProductWithQuantityInCart.value = result.data
                    _updateSuccessStatus.value = true
                }
            }
            else -> {
                val errorResult = (result as CustomisedResult.Error)
                errorResult.errorMessage?.let { returnErrorMessage(it) }
            }
        }
    }
}