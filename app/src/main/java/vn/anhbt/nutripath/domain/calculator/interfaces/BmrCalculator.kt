package vn.anhbt.nutripath.domain.calculator.interfaces

import vn.anhbt.nutripath.domain.model.Gender

interface BmrCalculator {
    operator fun invoke(
        weightKg: Double,
        heightCm: Double,
        age: Int,
        gender: Gender
    ): Double
}
