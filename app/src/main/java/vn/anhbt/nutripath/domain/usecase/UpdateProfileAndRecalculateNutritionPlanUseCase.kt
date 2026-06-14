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
import vn.anhbt.nutripath.domain.model.NutritionPlan
import vn.anhbt.nutripath.domain.model.UserProfile
import vn.anhbt.nutripath.domain.repository.NutritionGoalRepository
import vn.anhbt.nutripath.domain.repository.NutritionPlanRepository
import vn.anhbt.nutripath.domain.repository.UserRepository

class UpdateProfileAndRecalculateNutritionPlanUseCase(
    private val userRepository: UserRepository,
    private val nutritionGoalRepository: NutritionGoalRepository,
    private val nutritionPlanRepository: NutritionPlanRepository,
    private val bmrCalculator: BmrCalculator,
    private val tdeeCalculator: TdeeCalculator,
    private val targetCaloriesCalculator: TargetCaloriesCalculator,
    private val macroCalculator: MacroCalculator,
    private val goalWeeksCalculator: GoalWeeksCalculator
) : BaseSuspendUseCase<UserProfile, LoadResult<NutritionPlan>>() {

    override suspend fun execute(params: UserProfile): LoadResult<NutritionPlan> {
        val userProfile = params

        val updateProfileResult = userRepository.updateUserProfile(userProfile)
        if (updateProfileResult is Resource.Error) {
            return LoadResult.Error(updateProfileResult.throwable, "Update profile failed")
        }

        val activeGoal = when (val r = nutritionGoalRepository.getActiveGoal(userProfile.id)) {
            is Resource.Error -> return LoadResult.Error(r.throwable, "Fetch active goal failed")
            is Resource.Success -> r.data
                ?: return LoadResult.Error(message = "No active nutrition goal for user ${userProfile.id}")
        }

        val deactivateResult = nutritionPlanRepository.deactivateAllPlans(userProfile.id)
        if (deactivateResult is Resource.Error) {
            return LoadResult.Error(deactivateResult.throwable, "Deactivate previous plans failed")
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
            goalType = activeGoal.goalType,
            goalSpeed = activeGoal.goalSpeed
        )
        val proteinG = macroCalculator.protein(userProfile.weightKg, activeGoal.goalType)
        val fatG = macroCalculator.fat(goalCalories)
        val carbG = macroCalculator.carb(goalCalories, proteinG, fatG)
        val estimatedGoalWeeks = goalWeeksCalculator(
            currentWeightKg = userProfile.weightKg,
            targetWeightKg = activeGoal.targetWeightKg,
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
            currentWeightKg = userProfile.weightKg,
            targetWeightKg = activeGoal.targetWeightKg,
            estimatedGoalWeeks = estimatedGoalWeeks,
            nutritionGoalId = activeGoal.id,
            createdAt = Instant.now(),
            isActive = true
        )

        return when (val r = nutritionPlanRepository.savePlan(plan)) {
            is Resource.Error -> LoadResult.Error(r.throwable, "Save Nutrition Plan failed")
            is Resource.Success -> LoadResult.Success(plan.copy(id = r.data))
        }
    }
}
