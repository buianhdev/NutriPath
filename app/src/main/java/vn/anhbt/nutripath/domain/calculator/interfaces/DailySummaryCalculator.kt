package vn.anhbt.nutripath.domain.calculator.interfaces

import java.time.Instant
import vn.anhbt.nutripath.domain.model.DailyLogSummary
import vn.anhbt.nutripath.domain.model.MealEntry
import vn.anhbt.nutripath.domain.model.NutritionPlan

interface DailySummaryCalculator {
    operator fun invoke(
        date: Instant,
        plan: NutritionPlan,
        mealEntries: List<MealEntry>
    ): DailyLogSummary
}
