package com.app.cheatbite.core.domain.util

import com.app.cheatbite.core.domain.util.Result.Error
import com.app.cheatbite.core.domain.util.Result.Loading
import com.app.cheatbite.core.domain.util.Result.Success


sealed class Result<out T, out E : CallError> {
    data class Success<out T>(val data: T, val message: String? = null) : Result<T, Nothing>()
    data class Error<out E : CallError>(val error: E, val message: String? = null) : Result<Nothing, E>()
    data class Loading<out T>(val data: T, val message: String? = null) : Result<T, Nothing>()
}

// Helpers
inline fun <T, E: CallError> Result<T, E>.onSuccess(block: (T, String?) -> Unit): Result<T, E> {
    if (this is Success) block(data, message)
    return this
}

inline fun <T, E: CallError> Result<T, E>.onError(block: (E, String?) -> Unit): Result<T, E> {
    if (this is Error) block(error, message)
    return this
}

inline fun <T, E: CallError> Result<T, E>.onLoading(block: () -> Unit): Result<T, E> {
    if (this is Loading) block()
    return this
}

inline fun <T, E: CallError> Result<T, E>.onLoading(block: (T, String?) -> Unit): Result<T, E> {
    if (this is Loading) block(data, message)
    return this
}

inline fun <T, E: CallError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Error -> Error(error, message)
        is Success -> Success(map(data))
        is Loading -> Loading(map(data), message)
    }
}