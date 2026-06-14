package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.TdeeCalculator
import vn.anhbt.nutripath.domain.model.PAL

class TdeeCalculatorImpl : TdeeCalculator {

    override fun invoke(bmr: Double, pal: PAL): Double = bmr * pal.factor
}
