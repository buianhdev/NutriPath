package vn.anhbt.nutripath.domain.usecase

import java.time.Instant
import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.usecase.BaseSuspendUseCase
import vn.anhbt.nutripath.domain.calculator.interfaces.BmrCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.GoalWeeksCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.MacroCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.TargetCaloriesCalculator
import vn.anhbt.nutripath.domain.calculator.interfaces.TdeeCalculator
import vn.anhbt.nutripath.domain.common.LoadResult
import vn.anhbt.nutripath.domain.model.NutritionGoal
import vn.anhbt.nutripath.domain.model.NutritionPlan
import vn.anhbt.nutripath.domain.model.UserProfile
import vn.anhbt.nutripath.domain.repository.NutritionGoalRepository
import vn.anhbt.nutripath.domain.repository.NutritionPlanRepository
import vn.anhbt.nutripath.domain.repository.UserRepository

class SaveOnboardingUseCase(
    private val userRepository: UserRepository,
    private val nutritionGoalRepository: NutritionGoalRepository,
    private val nutritionPlanRepository: NutritionPlanRepository,
    private val bmrCalculator: BmrCalculator,
    private val tdeeCalculator: TdeeCalculator,
    private val targetCaloriesCalculator: TargetCaloriesCalculator,
    private val macroCalculator: MacroCalculator,
    private val goalWeeksCalculator: GoalWeeksCalculator
) : BaseSuspendUseCase<OnboardingData, LoadResult<NutritionPlan>>() {

    override suspend fun execute(params: OnboardingData): LoadResult<NutritionPlan> {
        val userProfile = params.userProfile
        val nutritionGoal = params.nutritionGoal

        val userId = when (val r = userRepository.saveUserProfile(userProfile)) {
            is Resource.Error -> return LoadResult.Error(r.throwable, "Save profile to database failed")
            is Resource.Success -> r.data
        }

        val goalId = when (val r = nutritionGoalRepository.saveGoal(nutritionGoal.copy(userId = userId))) {
            is Resource.Error -> return LoadResult.Error(r.throwable, "Save Nutrition Goal to database failed")
            is Resource.Success -> r.data
        }

        val bmr = bmrCalculator(
            weightKg = userProfile.weightKg,
            heightCm = userProfile.heightCm,
            age = userProfile.age,
            gender = userProfile.gender
        )
        val tdee = tdeeCalculator(bmr = bmr, pal = userProfile.pal)
        val goalCalories = targetCaloriesCalculator(
            tdee = tdee,
            goalType = nutritionGoal.goalType,
            goalSpeed = nutritionGoal.goalSpeed
        )
        val proteinG = macroCalculator.protein(userProfile.weightKg, nutritionGoal.goalType)
        val fatG = macroCalculator.fat(goalCalories)
        val carbG = macroCalculator.carb(goalCalories, proteinG, fatG)
        val estimatedGoalWeeks = goalWeeksCalculator(
            currentWeightKg = nutritionGoal.startWeightKg,
            targetWeightKg = nutritionGoal.targetWeightKg,
            tdee = tdee,
            targetCalories = goalCalories
        )
        val estimatedKgPerWeek = goalWeeksCalculator.kgPerWeek(tdee, goalCalories)

        val plan = NutritionPlan(
            bmr = bmr,
            tdee = tdee,
            goalCalories = goalCalories,
            proteinG = proteinG,
            fatG = fatG,
            carbG = carbG,
            estimatedWeightChangePerWeek = estimatedKgPerWeek,
            currentWeightKg = nutritionGoal.startWeightKg,
            targetWeightKg = nutritionGoal.targetWeightKg,
            estimatedGoalWeeks = estimatedGoalWeeks,
            nutritionGoalId = goalId,
            createdAt = Instant.now(),
            isActive = true
        )

        return when (val r = nutritionPlanRepository.savePlan(plan)) {
            is Resource.Error -> LoadResult.Error(r.throwable, "Save Nutrition Plan to database failed")
            is Resource.Success -> LoadResult.Success(plan.copy(id = r.data))
        }
    }
}

data class OnboardingData(
    val userProfile: UserProfile,
    val nutritionGoal: NutritionGoal
)
