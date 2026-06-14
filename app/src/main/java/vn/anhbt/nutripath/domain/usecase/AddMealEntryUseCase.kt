package vn.anhbt.nutripath.domain.usecase

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.usecase.BaseSuspendUseCase
import vn.anhbt.nutripath.domain.common.LoadResult
import vn.anhbt.nutripath.domain.model.MealEntry
import vn.anhbt.nutripath.domain.repository.MealEntryRepository

class AddMealEntryUseCase(
    private val mealEntryRepository: MealEntryRepository
) : BaseSuspendUseCase<MealEntry, LoadResult<Unit>>() {

    override suspend fun execute(params: MealEntry): LoadResult<Unit> =
        when (val r = mealEntryRepository.saveMealEntry(params)) {
            is Resource.Error -> LoadResult.Error(r.throwable, "Save meal entry failed")
            is Resource.Success -> LoadResult.Success(Unit)
        }
}
