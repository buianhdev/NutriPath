package vn.anhbt.nutripath.domain.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.NutritionGoal

interface NutritionGoalRepository {

    suspend fun getGoalById(id: Long): Resource<NutritionGoal?>

    suspend fun saveGoal(goal: NutritionGoal): Resource<Long>

    suspend fun updateGoal(goal: NutritionGoal): Resource<Unit>

    suspend fun deleteGoal(id: Long): Resource<Unit>
}
