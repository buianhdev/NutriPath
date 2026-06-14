package vn.anhbt.nutripath.domain.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.NutritionPlan

interface NutritionPlanRepository {

    suspend fun getPlanById(id: Long): Resource<NutritionPlan?>

    suspend fun getActivePlan(): Resource<NutritionPlan?>

    suspend fun savePlan(plan: NutritionPlan): Resource<Long>

    suspend fun updatePlan(plan: NutritionPlan): Resource<Unit>

    suspend fun deletePlan(id: Long): Resource<Unit>

    suspend fun deactivateAllPlans(userId: Long): Resource<Unit>
}
