package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.FatCalculator

class FatCalculatorImpl : FatCalculator {

    override fun invoke(targetCalories: Double): Double = (targetCalories * 0.25) / 9.0
}
