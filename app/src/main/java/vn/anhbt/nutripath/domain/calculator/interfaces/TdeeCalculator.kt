package vn.anhbt.nutripath.domain.calculator.interfaces

import vn.anhbt.nutripath.domain.model.PAL

interface TdeeCalculator {
    operator fun invoke(bmr: Double, pal: PAL): Double
}
