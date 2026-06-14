package vn.anhbt.nutripath.domain.calculator.interfaces

import vn.anhbt.nutripath.domain.model.GoalType

interface MacroCalculator {

    fun protein(weightKg: Double, goalType: GoalType): Double

    fun fat(targetCalories: Double): Double

    fun carb(targetCalories: Double, proteinG: Double, fatG: Double): Double
}
