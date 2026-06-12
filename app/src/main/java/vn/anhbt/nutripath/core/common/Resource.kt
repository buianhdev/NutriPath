package vn.anhbt.nutripath.core.common

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error(
        val throwable: Throwable,
        val message: String? = throwable.message
    ) : Resource<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = (this as? Success)?.data

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw throwable
    }

    fun getOrElse(fallback: () -> @UnsafeVariance T): T = when (this) {
        is Success -> data
        is Error -> fallback()
    }
}

inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error -> this
}

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onError(action: (Throwable) -> Unit): Resource<T> {
    if (this is Resource.Error) action(throwable)
    return this
}

inline fun <T> resourceOf(block: () -> T): Resource<T> = try {
    Resource.Success(block())
} catch (t: Throwable) {
    Resource.Error(t)
}
