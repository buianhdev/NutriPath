package vn.anhbt.nutripath.di

import org.koin.dsl.module
import vn.anhbt.nutripath.domain.calculator.impl.BmrCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.CarbCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.DailySummaryCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.FatCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.GoalWeeksCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.ProteinCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.ScaledNutritionFoodCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.TargetCaloriesCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.TdeeCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.interfaces.BmrCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.CarbCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.DailySummaryCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.FatCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.GoalWeeksCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.ProteinCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.ScaledNutritionFoodCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.TargetCaloriesCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.TdeeCalculator

val domainModule = module {
    factory<BmrCalculator> { BmrCalculatorImpl() }
    factory<TdeeCalculator> { TdeeCalculatorImpl() }
    factory<TargetCaloriesCalculator> { TargetCaloriesCalculatorImpl() }
    factory<ProteinCalculator> { ProteinCalculatorImpl() }
    factory<FatCalculator> { FatCalculatorImpl() }
    factory<CarbCalculator> { CarbCalculatorImpl() }
    factory<GoalWeeksCalculator> { GoalWeeksCalculatorImpl() }
    factory<ScaledNutritionFoodCalculator> { ScaledNutritionFoodCalculatorImpl() }
    factory<DailySummaryCalculator> { DailySummaryCalculatorImpl() }
}
