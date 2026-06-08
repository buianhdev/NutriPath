package vn.anhbt.nutripath.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

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