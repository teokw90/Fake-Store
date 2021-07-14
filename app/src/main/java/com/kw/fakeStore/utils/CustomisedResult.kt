package com.kw.fakeStore.utils

sealed class CustomisedResult<out R> {
    data class Success<out T>(val data: T): CustomisedResult<T>()
    data class Error(val errorCode: Int, val errorMessage: String?): CustomisedResult<Nothing>()
    object Loading: CustomisedResult<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorCode=$errorCode, errorMessage=$errorMessage]"
            Loading -> "Loading"
        }
    }
}

enum class ResultCustomErrorType(val code: Int, val message: String) {
    NETWORK_ERROR(400, "No network connection"),
    NO_RECORD_FOUND_ERROR(500, "Record not found."),
    TRANSACTION_FAILED(600, "Transaction unsuccessful."),
    SOMETHING_WENT_WRONG_ERROR(999, "Something went wrong.")
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */

val CustomisedResult<*>.succeeded
    get() = this is CustomisedResult.Success && data != null

