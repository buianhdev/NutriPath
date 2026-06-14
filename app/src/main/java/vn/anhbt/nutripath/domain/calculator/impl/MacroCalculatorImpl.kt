package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.MacroCalculator
import vn.anhbt.nutripath.domain.model.GoalType

class MacroCalculatorImpl : MacroCalculator {

    override fun protein(weightKg: Double, goalType: GoalType): Double {
        val ratio = when (goalType) {
            GoalType.MAINTAIN -> 1.4
            GoalType.LOSE, GoalType.GAIN -> 1.6
        }
        return weightKg * ratio
    }

    override fun fat(targetCalories: Double): Double = (targetCalories * 0.25) / 9.0

    override fun carb(targetCalories: Double, proteinG: Double, fatG: Double): Double {
        val proteinCalories = proteinG * 4.0
        val fatCalories = fatG * 9.0
        val remainingCalories = targetCalories - proteinCalories - fatCalories

        require(remainingCalories >= 0.0) {
            "Invalid macro plan: remaining calories for carbs is negative."
        }

        return remainingCalories / 4.0
    }
}
