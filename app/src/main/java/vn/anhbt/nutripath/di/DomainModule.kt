package vn.anhbt.nutripath.di

import org.koin.dsl.module
import vn.anhbt.nutripath.domain.calculator.impl.BmrCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.DailySummaryCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.GoalWeeksCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.MacroCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.ScaledNutritionFoodCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.TargetCaloriesCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.impl.TdeeCalculatorImpl
import vn.anhbt.nutripath.domain.calculator.interfaces.BmrCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.DailySummaryCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.GoalWeeksCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.MacroCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.ScaledNutritionFoodCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.TargetCaloriesCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.TdeeCalculator
import vn.anhbt.nutripath.domain.usecase.AddMealEntryUseCase
import vn.anhbt.nutripath.domain.usecase.GetActiveNutritionPlanUseCase
import vn.anhbt.nutripath.domain.usecase.GetDailyLogSummaryUseCase
import vn.anhbt.nutripath.domain.usecase.GetUserProfileUseCase
import vn.anhbt.nutripath.domain.usecase.RemoveMealEntryUseCase
import vn.anhbt.nutripath.domain.usecase.SaveOnboardingUseCase
import vn.anhbt.nutripath.domain.usecase.SearchFoodUseCase
import vn.anhbt.nutripath.domain.usecase.UpdateProfileAndRecalculateNutritionPlanUseCase

val domainModule = module {
    factory<BmrCalculator> { BmrCalculatorImpl() }
    factory<TdeeCalculator> { TdeeCalculatorImpl() }
    factory<TargetCaloriesCalculator> { TargetCaloriesCalculatorImpl() }
    factory<MacroCalculator> { MacroCalculatorImpl() }
    factory<GoalWeeksCalculator> { GoalWeeksCalculatorImpl() }
    factory<ScaledNutritionFoodCalculator> { ScaledNutritionFoodCalculatorImpl() }
    factory<DailySummaryCalculator> { DailySummaryCalculatorImpl() }

    factory { SaveOnboardingUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { UpdateProfileAndRecalculateNutritionPlanUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { GetUserProfileUseCase(get()) }
    factory { GetActiveNutritionPlanUseCase(get()) }
    factory { GetDailyLogSummaryUseCase(get(), get(), get()) }
    factory { AddMealEntryUseCase(get()) }
    factory { RemoveMealEntryUseCase(get()) }
    factory { SearchFoodUseCase(get()) }
}
