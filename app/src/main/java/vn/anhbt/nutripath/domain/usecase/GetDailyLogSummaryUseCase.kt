package vn.anhbt.nutripath.domain.usecase

import java.time.Instant
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.usecase.BaseUseCaseFlow
import vn.anhbt.nutripath.domain.calculator.interfaces.DailySummaryCalculator
import vn.anhbt.nutripath.domain.common.LoadResult
import vn.anhbt.nutripath.domain.model.DailyLogSummary
import vn.anhbt.nutripath.domain.repository.MealEntryRepository
import vn.anhbt.nutripath.domain.repository.NutritionPlanRepository

class GetDailyLogSummaryUseCase(
    private val mealEntryRepository: MealEntryRepository,
    private val nutritionPlanRepository: NutritionPlanRepository,
    private val dailySummaryCalculator: DailySummaryCalculator,
) : BaseUseCaseFlow<GetDailyLogData, LoadResult<DailyLogSummary>>() {

    override fun execute(params: GetDailyLogData): Flow<LoadResult<DailyLogSummary>> {
        return mealEntryRepository
            .observeMealEntriesByDate(params.userId, params.date)
            .map { mealEntries ->
                val nutritionPlan = when (val r = nutritionPlanRepository.getActivePlan()) {
                    is Resource.Error -> {
                        return@map LoadResult.Error(
                            r.throwable,
                            "Fetch active nutrition plan failed"
                        )
                    }

                    is Resource.Success -> r.data ?: return@map LoadResult.Error(
                        message = "No active nutrition plan"
                    )
                }

                LoadResult.Success(
                    dailySummaryCalculator(
                        params.instant,
                        nutritionPlan,
                        mealEntries
                    )
                )
            }
            .catch { e ->
                emit(LoadResult.Error(e, "Observe daily log summary failed"))
            }
    }
}

data class GetDailyLogData(
    val date: LocalDate,
    val instant: Instant,
    val userId: Long
)
