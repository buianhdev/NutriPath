package vn.anhbt.nutripath.domain.calculator.impl

import vn.anhbt.nutripath.domain.calculator.interfaces.ScaledNutritionFoodCalculator
import vn.anhbt.nutripath.domain.model.Food
import vn.anhbt.nutripath.domain.model.MacroBreakdown

class ScaledNutritionFoodCalculatorImpl : ScaledNutritionFoodCalculator {

    override fun invoke(food: Food, inputAmountG: Double): MacroBreakdown {
        require(food.baseAmountG > 0.0) { "Food baseAmountG must be > 0" }
        val ratio = inputAmountG / food.baseAmountG
        return MacroBreakdown(
            calories = food.caloriesPerBase * ratio,
            proteinG = food.proteinPerBase * ratio,
            carbG = food.carbPerBase * ratio,
            fatG = food.fatPerBase * ratio,
        )
    }
}
