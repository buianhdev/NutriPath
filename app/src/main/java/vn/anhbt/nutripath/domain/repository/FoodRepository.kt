package vn.anhbt.nutripath.domain.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.Food

interface FoodRepository {

    suspend fun getFoodById(id: Long): Resource<Food?>

    suspend fun saveFood(food: Food): Resource<Long>

    suspend fun updateFood(food: Food): Resource<Unit>

    suspend fun deleteFood(id: Long): Resource<Unit>
}
