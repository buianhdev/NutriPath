package vn.anhbt.nutripath.domain.repository

import java.time.Instant
import kotlinx.coroutines.flow.Flow
import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.MealEntry

interface MealEntryRepository {

    suspend fun getMealEntryById(id: Long): Resource<MealEntry?>

    suspend fun getMealEntriesByDateRange(start: Instant, end: Instant): Resource<List<MealEntry>>

    fun observeMealEntriesByDateRange(start: Instant, end: Instant): Flow<List<MealEntry>>

    suspend fun addMealEntry(entry: MealEntry): Resource<Long>

    suspend fun updateMealEntry(entry: MealEntry): Resource<Unit>

    suspend fun deleteMealEntry(id: Long): Resource<Unit>
}
