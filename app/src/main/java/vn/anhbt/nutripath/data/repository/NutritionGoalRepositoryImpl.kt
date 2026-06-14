package vn.anhbt.nutripath.data.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.common.resourceOf
import vn.anhbt.nutripath.data.local.dao.NutritionGoalDao
import vn.anhbt.nutripath.data.mapper.toDomain
import vn.anhbt.nutripath.data.mapper.toEntity
import vn.anhbt.nutripath.domain.model.NutritionGoal
import vn.anhbt.nutripath.domain.repository.NutritionGoalRepository

class NutritionGoalRepositoryImpl(
    private val nutritionGoalDao: NutritionGoalDao
) : NutritionGoalRepository {

    override suspend fun getGoalById(id: Long): Resource<NutritionGoal?> =
        resourceOf { nutritionGoalDao.getById(id)?.toDomain() }

    override suspend fun getActiveGoal(userId: Long): Resource<NutritionGoal?> =
        resourceOf { nutritionGoalDao.getActiveGoal(userId)?.toDomain() }

    override suspend fun saveGoal(goal: NutritionGoal): Resource<Long> =
        resourceOf { nutritionGoalDao.insert(goal.toEntity()) }

    override suspend fun updateGoal(goal: NutritionGoal): Resource<Unit> =
        resourceOf { nutritionGoalDao.update(goal.toEntity()) }

    override suspend fun deleteGoal(id: Long): Resource<Unit> =
        resourceOf { nutritionGoalDao.deleteById(id) }
}
