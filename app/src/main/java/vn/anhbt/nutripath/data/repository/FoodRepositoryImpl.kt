package vn.anhbt.nutripath.data.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.common.resourceOf
import vn.anhbt.nutripath.data.local.dao.FoodDao
import vn.anhbt.nutripath.data.mapper.toDomain
import vn.anhbt.nutripath.data.mapper.toEntity
import vn.anhbt.nutripath.domain.model.Food
import vn.anhbt.nutripath.domain.repository.FoodRepository

class FoodRepositoryImpl(
    private val foodDao: FoodDao
) : FoodRepository {

    override suspend fun getFoodById(id: Long): Resource<Food?> =
        resourceOf { foodDao.getById(id)?.toDomain() }

    override suspend fun getAllFoods(): Resource<List<Food>> =
        resourceOf { foodDao.getAll().map { it.toDomain() } }

    override suspend fun searchFoods(query: String): Resource<List<Food>> =
        resourceOf { foodDao.search(query).map { it.toDomain() } }

    override suspend fun saveFood(food: Food): Resource<Long> =
        resourceOf { foodDao.insert(food.toEntity()) }

    override suspend fun deleteFood(id: Long): Resource<Unit> =
        resourceOf { foodDao.deleteById(id) }
}
