package vn.anhbt.nutripath.domain.calculator.interfaces

import vn.anhbt.nutripath.domain.model.Food
import vn.anhbt.nutripath.domain.model.MacroBreakdown

interface ScaledNutritionFoodCalculator {
    operator fun invoke(food: Food, inputAmountG: Double): MacroBreakdown
}
