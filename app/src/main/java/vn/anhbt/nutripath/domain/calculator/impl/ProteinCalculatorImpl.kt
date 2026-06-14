package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.ProteinCalculator
import vn.anhbt.nutripath.domain.model.GoalType

class ProteinCalculatorImpl : ProteinCalculator {

    override fun invoke(weightKg: Double, goalType: GoalType): Double {
        val ratio = when (goalType) {
            GoalType.MAINTAIN -> 1.4
            GoalType.LOSE, GoalType.GAIN -> 1.6
        }
        return weightKg * ratio
    }
}
