package vn.anhbt.nutripath.domain.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.Food

interface FoodRepository {

    suspend fun getFoodById(id: Long): Resource<Food?>

    suspend fun getAllFoods(): Resource<List<Food>>

    suspend fun searchFoods(query: String): Resource<List<Food>>

    suspend fun saveFood(food: Food): Resource<Long>

    suspend fun deleteFood(id: Long): Resource<Unit>
}
