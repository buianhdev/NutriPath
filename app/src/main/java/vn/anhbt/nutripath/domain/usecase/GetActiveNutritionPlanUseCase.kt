package vn.anhbt.nutripath.domain.usecase

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.usecase.BaseSuspendUseCase
import vn.anhbt.nutripath.core.usecase.NoParams
import vn.anhbt.nutripath.domain.common.LoadResult
import vn.anhbt.nutripath.domain.model.NutritionPlan
import vn.anhbt.nutripath.domain.repository.NutritionPlanRepository

class GetActiveNutritionPlanUseCase(
    private val nutritionPlanRepository: NutritionPlanRepository
) : BaseSuspendUseCase<NoParams, LoadResult<NutritionPlan>>() {

    override suspend fun execute(params: NoParams): LoadResult<NutritionPlan> =
        when (val result = nutritionPlanRepository.getActivePlan()) {
            is Resource.Error -> LoadResult.Error(result.throwable, "Fetch active nutrition plan failed")
            is Resource.Success -> result.data
                ?.let { LoadResult.Success(it) }
                ?: LoadResult.Error(message = "No active nutrition plan")
        }
}
