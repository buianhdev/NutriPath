package vn.anhbt.nutripath.domain.usecase

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.usecase.BaseSuspendUseCase
import vn.anhbt.nutripath.domain.common.LoadResult
import vn.anhbt.nutripath.domain.model.Food
import vn.anhbt.nutripath.domain.repository.FoodRepository

class SearchFoodUseCase(
    private val foodRepository: FoodRepository
) : BaseSuspendUseCase<String, LoadResult<List<Food>>>() {

    override suspend fun execute(params: String): LoadResult<List<Food>> =
        when (val r = foodRepository.searchFoods(params.lowercase())) {
            is Resource.Error -> LoadResult.Error(r.throwable, "Search foods failed")
            is Resource.Success -> LoadResult.Success(r.data)
        }
}
