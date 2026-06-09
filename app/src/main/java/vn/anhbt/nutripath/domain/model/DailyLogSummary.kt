package vn.anhbt.nutripath.domain.model

import java.time.LocalDate

data class DailyLogSummary(
    val date: LocalDate,
    val mealEntries: List<MealEntry>,
    val targetCalories: Double,
    val consumedCalories: Double,
    val remainingCalories: Double,
    val targetMacros: MacroBreakdown,
    val consumedMacros: MacroBreakdown,
    val remainingMacros: MacroBreakdown
)
