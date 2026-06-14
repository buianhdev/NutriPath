package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.TargetCaloriesCalculator
import vn.anhbt.nutripath.domain.model.GoalSpeed
import vn.anhbt.nutripath.domain.model.GoalType

class TargetCaloriesCalculatorImpl : TargetCaloriesCalculator {

    override fun invoke(tdee: Double, goalType: GoalType, goalSpeed: GoalSpeed): Double =
        tdee + calorieAdjustment(goalType, goalSpeed)

    private fun calorieAdjustment(goalType: GoalType, goalSpeed: GoalSpeed): Double =
        when (goalType) {
            GoalType.MAINTAIN -> 0.0
            GoalType.LOSE -> when (goalSpeed) {
                GoalSpeed.LOW -> -250.0
                GoalSpeed.NORMAL -> -500.0
                GoalSpeed.FAST -> -750.0
            }
            GoalType.GAIN -> when (goalSpeed) {
                GoalSpeed.LOW -> 250.0
                GoalSpeed.NORMAL -> 400.0
                GoalSpeed.FAST -> 600.0
            }
        }
}
