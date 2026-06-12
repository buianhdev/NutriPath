package vn.anhbt.nutripath.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.NutritionGoal

interface NutritionGoalRepository {

    suspend fun getGoalById(id: Long): Resource<NutritionGoal?>

    suspend fun getActiveGoal(userId: Long): Resource<NutritionGoal?>

    fun observeActiveGoal(userId: Long): Flow<NutritionGoal?>

    suspend fun getGoalHistory(userId: Long): Resource<List<NutritionGoal>>

    suspend fun saveGoal(userId: Long, goal: NutritionGoal): Resource<Long>

    suspend fun deactivateAll(userId: Long): Resource<Unit>

    suspend fun deleteGoal(id: Long): Resource<Unit>
}
