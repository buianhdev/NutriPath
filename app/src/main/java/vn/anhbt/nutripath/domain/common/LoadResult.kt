package vn.anhbt.nutripath.domain.common

sealed class LoadResult<out T> {

    data object Loading : LoadResult<Nothing>()

    data class Success<out T>(val data: T) : LoadResult<T>()

    data class Error(
        val throwable: Throwable? = null,
        val message: String? = throwable?.message
    ) : LoadResult<Nothing>()

    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = (this as? Success)?.data

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw (throwable ?: IllegalStateException(message ?: "LoadResult.Error"))
        is Loading -> throw IllegalStateException("LoadResult is still Loading")
    }

    fun getOrElse(fallback: () -> @UnsafeVariance T): T = when (this) {
        is Success -> data
        is Error, is Loading -> fallback()
    }
}

inline fun <T, R> LoadResult<T>.map(transform: (T) -> R): LoadResult<R> = when (this) {
    is LoadResult.Success -> LoadResult.Success(transform(data))
    is LoadResult.Error -> this
    is LoadResult.Loading -> this
}

inline fun <T> LoadResult<T>.onLoading(action: () -> Unit): LoadResult<T> {
    if (this is LoadResult.Loading) action()
    return this
}

inline fun <T> LoadResult<T>.onSuccess(action: (T) -> Unit): LoadResult<T> {
    if (this is LoadResult.Success) action(data)
    return this
}

inline fun <T> LoadResult<T>.onError(action: (Throwable?) -> Unit): LoadResult<T> {
    if (this is LoadResult.Error) action(throwable)
    return this
}
