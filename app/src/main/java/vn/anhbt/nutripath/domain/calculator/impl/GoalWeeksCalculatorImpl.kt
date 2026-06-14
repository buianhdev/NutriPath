package vn.anhbt.nutripath.domain.calculator.impl

import kotlin.math.abs
import vn.anhbt.nutripath.domain.calculator.interfaces.GoalWeeksCalculator

class GoalWeeksCalculatorImpl : GoalWeeksCalculator {

    override fun invoke(
        currentWeightKg: Double,
        targetWeightKg: Double,
        tdee: Double,
        targetCalories: Double
    ): Double? {
        val weightDiff = abs(targetWeightKg - currentWeightKg)
        val calorieGapPerDay = abs(targetCalories - tdee)

        if (weightDiff <= 0.0) return 0.0
        if (calorieGapPerDay <= 0.0) return null

        val estimatedKgPerWeek = (calorieGapPerDay * 7.0) / 7700.0
        if (estimatedKgPerWeek <= 0.0) return null

        return weightDiff / estimatedKgPerWeek
    }
}
