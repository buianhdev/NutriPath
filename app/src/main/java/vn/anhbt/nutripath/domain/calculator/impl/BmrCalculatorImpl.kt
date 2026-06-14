package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.BmrCalculator
import vn.anhbt.nutripath.domain.model.Gender

class BmrCalculatorImpl : BmrCalculator {

    override fun invoke(
        weightKg: Double,
        heightCm: Double,
        age: Int,
        gender: Gender
    ): Double {
        val genderFactor = if (gender == Gender.MALE) 5.0 else -161.0
        return 10.0 * weightKg + 6.25 * heightCm - 5.0 * age + genderFactor
    }
}
