package vn.anhbt.nutripath.domain.calculator.interfaces

interface CarbCalculator {
    operator fun invoke(targetCalories: Double, proteinG: Double, fatG: Double): Double
}
