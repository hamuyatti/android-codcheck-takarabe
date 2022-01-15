package jp.co.yumemi.android.code_check.utils

import retrofit2.HttpException
import retrofit2.Response


suspend fun <T> asyncFetch(
    apiFunction: suspend () -> Response<T>,
): T {
    return try {
        val response = apiFunction.invoke()
        if (response.isSuccessful) {
            response.body()!!
        } else throw HttpException(response)
    } catch (throwable: Throwable) {
        throw throwable
    }
}

