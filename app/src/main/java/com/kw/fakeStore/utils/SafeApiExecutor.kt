package com.kw.fakeStore.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class SafeApiExecutor {
    companion object {
        suspend fun<T> execute(apiCall: suspend() -> T): CustomisedResult<T> {
            return withContext(Dispatchers.IO) {
                try {
                    CustomisedResult.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> CustomisedResult.Error(
                            errorCode = 9999,
                            errorMessage = throwable.message
                        )
                        is HttpException -> CustomisedResult.Error(
                            errorCode = throwable.code(),
                            errorMessage = throwable.message()
                        )
                        else -> CustomisedResult.Error(errorCode = -1, errorMessage = "Unknown")
                    }
                }
            }
        }
    }
}