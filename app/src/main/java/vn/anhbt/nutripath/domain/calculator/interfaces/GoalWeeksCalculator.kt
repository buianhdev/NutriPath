package vn.anhbt.nutripath.domain.calculator.interfaces

interface GoalWeeksCalculator {
    operator fun invoke(
        currentWeightKg: Double,
        targetWeightKg: Double,
        tdee: Double,
        targetCalories: Double
    ): Double?
}
