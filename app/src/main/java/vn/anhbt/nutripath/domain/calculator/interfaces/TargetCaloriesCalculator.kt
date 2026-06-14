package vn.anhbt.nutripath.domain.calculator.interfaces

import vn.anhbt.nutripath.domain.model.GoalSpeed
import vn.anhbt.nutripath.domain.model.GoalType

interface TargetCaloriesCalculator {
    operator fun invoke(tdee: Double, goalType: GoalType, goalSpeed: GoalSpeed): Double
}
