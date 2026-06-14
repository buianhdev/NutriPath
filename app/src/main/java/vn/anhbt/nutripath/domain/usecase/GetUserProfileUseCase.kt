package vn.anhbt.nutripath.domain.usecase

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.usecase.BaseSuspendUseCase
import vn.anhbt.nutripath.core.usecase.NoParams
import vn.anhbt.nutripath.domain.common.LoadResult
import vn.anhbt.nutripath.domain.model.UserProfile
import vn.anhbt.nutripath.domain.repository.UserRepository

class GetUserProfileUseCase(
    private val userRepository: UserRepository
) : BaseSuspendUseCase<Long, LoadResult<UserProfile>>() {
    override suspend fun execute(params: Long): LoadResult<UserProfile> {
        return when(val result = userRepository.getUserProfile(params)) {
            is Resource.Error -> LoadResult.Error(result.throwable, result.message)
            is Resource.Success<UserProfile?> -> {
                if(result.data == null) {
                    LoadResult.Error(message = "The data is null")
                } else {
                    LoadResult.Success(result.data)
                }
            }
        }
     }
}