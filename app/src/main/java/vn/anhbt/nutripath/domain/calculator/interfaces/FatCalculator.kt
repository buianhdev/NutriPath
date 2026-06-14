package vn.anhbt.nutripath.domain.calculator.interfaces

interface FatCalculator {
    operator fun invoke(targetCalories: Double): Double
}
