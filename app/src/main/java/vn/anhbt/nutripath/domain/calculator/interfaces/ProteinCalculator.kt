package vn.anhbt.nutripath.domain.calculator.interfaces

import vn.anhbt.nutripath.domain.model.GoalType

interface ProteinCalculator {
    operator fun invoke(weightKg: Double, goalType: GoalType): Double
}
