package com.example.check.util

/**
 * A generic class that holds a value with its loading status
 * @param <T>
 */
sealed class Result<out T> {
    /**
     * success get data
     */
    data class Success<out T>(val data: T, val cached: Boolean = false) : Result<T>()

    /**
     * error get data
     */
    data class Error(val msg: ErrorMessage) : Result<Nothing>()

    @Deprecated("Use Error")
    data class OldError(val exception: Exception) : Result<Nothing>()

    @Deprecated("Use Error")
    /**
     * handled error code message
     */
    data class HandledError(val msg: ErrorMessage) : Result<Nothing>()

    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$msg]"
            is OldError -> "Error[exception=$exception]"
            is HandledError -> "HandledError[exception=$msg]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null


fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

/**
 * Handled network response exception
 */
class HandledException(msg: ErrorMessage) : RuntimeException(msg.toString())

/**
 * http response code
 */
data class ErrorMessage(
    val status: Int,
    val message: String
)


/**
 * this class is used in case not handled Exception
 */
class IllegalResponseException(msg: String) : java.lang.RuntimeException(msg)