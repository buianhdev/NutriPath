package vn.anhbt.nutripath.domain.calculator.impl

import java.time.Instant
import vn.anhbt.nutripath.domain.calculator.interfaces.DailySummaryCalculator
import vn.anhbt.nutripath.domain.model.DailyLogSummary
import vn.anhbt.nutripath.domain.model.MacroBreakdown
import vn.anhbt.nutripath.domain.model.MealEntry
import vn.anhbt.nutripath.domain.model.NutritionPlan

class DailySummaryCalculatorImpl : DailySummaryCalculator {

    override fun invoke(
        date: Instant,
        plan: NutritionPlan,
        mealEntries: List<MealEntry>
    ): DailyLogSummary {
        val targetMacros = MacroBreakdown(
            calories = plan.goalCalories,
            proteinG = plan.proteinG,
            carbG = plan.carbG,
            fatG = plan.fatG,
        )
        val consumedMacros = mealEntries.map { it.macroBreakdown }.sumMacros()
        val remainingMacros = MacroBreakdown(
            calories = targetMacros.calories - consumedMacros.calories,
            proteinG = targetMacros.proteinG - consumedMacros.proteinG,
            carbG = targetMacros.carbG - consumedMacros.carbG,
            fatG = targetMacros.fatG - consumedMacros.fatG,
        )

        return DailyLogSummary(
            date = date,
            mealEntries = mealEntries,
            targetCalories = targetMacros.calories,
            consumedCalories = consumedMacros.calories,
            remainingCalories = remainingMacros.calories,
            targetMacros = targetMacros,
            consumedMacros = consumedMacros,
            remainingMacros = remainingMacros,
        )
    }

    private fun List<MacroBreakdown>.sumMacros(): MacroBreakdown = MacroBreakdown(
        calories = sumOf { it.calories },
        proteinG = sumOf { it.proteinG },
        carbG = sumOf { it.carbG },
        fatG = sumOf { it.fatG },
    )
}
