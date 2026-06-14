package vn.anhbt.nutripath.domain.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.MealEntry

interface MealEntryRepository {

    suspend fun getMealEntryById(id: Long): Resource<MealEntry?>

    suspend fun saveMealEntry(entry: MealEntry): Resource<Long>

    suspend fun updateMealEntry(entry: MealEntry): Resource<Unit>

    suspend fun deleteMealEntry(id: Long): Resource<Unit>
}
