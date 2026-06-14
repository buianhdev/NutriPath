package vn.anhbt.nutripath.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseSuspendUseCase<in R, out Q> {

    suspend operator fun invoke(params: R): Q {
        return execute(params)
    }

    protected abstract suspend fun execute(params: R): Q
}

abstract class BaseUseCase<in R, out Q> {

    fun invoke(params: R): Q {
        return execute(params)
    }

    protected abstract fun execute(params: R): Q

}

abstract class BaseUseCaseFlow<in R, out Q> {

    fun invoke(params: R): Flow<Q> {
        return execute(params)
            .catch {  }
    }

    protected abstract fun execute (params: R): Flow<Q>

}

data object NoParams