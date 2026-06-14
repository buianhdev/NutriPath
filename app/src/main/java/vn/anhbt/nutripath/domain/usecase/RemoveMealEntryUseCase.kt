package vn.anhbt.nutripath.domain.usecase

import vn.anhbt.nutripath.core.usecase.BaseSuspendUseCase
import vn.anhbt.nutripath.core.usecase.NoParams
import vn.anhbt.nutripath.domain.repository.MealEntryRepository

class RemoveMealEntryUseCase(
    private val mealEntryRepository: MealEntryRepository
) : BaseSuspendUseCase<Long, Unit>() {

    override suspend fun execute(params: Long) {
        mealEntryRepository.deleteMealEntry(params)
    }
}