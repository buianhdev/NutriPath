package vn.anhbt.nutripath.data.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.common.resourceOf
import vn.anhbt.nutripath.data.local.dao.NutritionPlanDao
import vn.anhbt.nutripath.data.mapper.toDomain
import vn.anhbt.nutripath.data.mapper.toEntity
import vn.anhbt.nutripath.domain.model.NutritionPlan
import vn.anhbt.nutripath.domain.repository.NutritionPlanRepository

class NutritionPlanRepositoryImpl(
    private val nutritionPlanDao: NutritionPlanDao
) : NutritionPlanRepository {

    override suspend fun getPlanById(id: Long): Resource<NutritionPlan?> =
        resourceOf { nutritionPlanDao.getById(id)?.toDomain() }

    override suspend fun savePlan(plan: NutritionPlan): Resource<Long> =
        resourceOf { nutritionPlanDao.insert(plan.toEntity()) }

    override suspend fun updatePlan(plan: NutritionPlan): Resource<Unit> =
        resourceOf { nutritionPlanDao.update(plan.toEntity()) }

    override suspend fun deletePlan(id: Long): Resource<Unit> =
        resourceOf { nutritionPlanDao.deleteById(id) }
}
