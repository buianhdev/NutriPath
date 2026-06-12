package vn.anhbt.nutripath.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun observeActiveGoal(userId: Long): Flow<NutritionGoal?> =
        nutritionGoalDao.observeActiveGoal(userId).map { it?.toDomain() }

    override suspend fun getGoalHistory(userId: Long): Resource<List<NutritionGoal>> =
        resourceOf { nutritionGoalDao.getHistory(userId).map { it.toDomain() } }

    override suspend fun saveGoal(userId: Long, goal: NutritionGoal): Resource<Long> =
        resourceOf { nutritionGoalDao.insert(goal.toEntity(userId)) }

    override suspend fun deactivateAll(userId: Long): Resource<Unit> =
        resourceOf { nutritionGoalDao.deactivateAll(userId) }

    override suspend fun deleteGoal(id: Long): Resource<Unit> =
        resourceOf { nutritionGoalDao.deleteById(id) }
}
