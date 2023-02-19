package com.luisma.core.models

sealed class BaseResponse<T>(data: T? = null) {
    class Success<T>(val data: T) : BaseResponse<T>(
        data = data
    )

    class Failure<T>(
        data: T? = null,
        val message: String? = null
    ) : BaseResponse<T>(data = data)
}