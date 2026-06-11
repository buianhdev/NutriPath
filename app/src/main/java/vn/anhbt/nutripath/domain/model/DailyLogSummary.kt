package vn.anhbt.nutripath.domain.model

import java.time.Instant

data class DailyLogSummary(
    val date: Instant,
    val mealEntries: List<MealEntry>,
    val targetCalories: Double,
    val consumedCalories: Double,
    val remainingCalories: Double,
    val targetMacros: MacroBreakdown,
    val consumedMacros: MacroBreakdown,
    val remainingMacros: MacroBreakdown
)
