package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.CarbCalculator

class CarbCalculatorImpl : CarbCalculator {

    override fun invoke(targetCalories: Double, proteinG: Double, fatG: Double): Double {
        val proteinCalories = proteinG * 4.0
        val fatCalories = fatG * 9.0
        val remainingCalories = targetCalories - proteinCalories - fatCalories

        require(remainingCalories >= 0.0) {
            "Invalid macro plan: remaining calories for carbs is negative."
        }

        return remainingCalories / 4.0
    }
}
