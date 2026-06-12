package vn.anhbt.nutripath.data.repository

import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.common.resourceOf
import vn.anhbt.nutripath.data.local.dao.MealEntryDao
import vn.anhbt.nutripath.data.mapper.toDomain
import vn.anhbt.nutripath.data.mapper.toEntity
import vn.anhbt.nutripath.domain.model.MealEntry
import vn.anhbt.nutripath.domain.repository.MealEntryRepository

class MealEntryRepositoryImpl(
    private val mealEntryDao: MealEntryDao
) : MealEntryRepository {

    override suspend fun getMealEntryById(id: Long): Resource<MealEntry?> =
        resourceOf { mealEntryDao.getById(id)?.toDomain() }

    override suspend fun getMealEntriesByDateRange(
        start: Instant,
        end: Instant
    ): Resource<List<MealEntry>> = resourceOf {
        mealEntryDao.getByDateRange(start.toEpochMilli(), end.toEpochMilli())
            .map { it.toDomain() }
    }

    override fun observeMealEntriesByDateRange(
        start: Instant,
        end: Instant
    ): Flow<List<MealEntry>> =
        mealEntryDao.observeByDateRange(start.toEpochMilli(), end.toEpochMilli())
            .map { list -> list.map { it.toDomain() } }

    override suspend fun addMealEntry(entry: MealEntry): Resource<Long> =
        resourceOf { mealEntryDao.insert(entry.toEntity()) }

    override suspend fun updateMealEntry(entry: MealEntry): Resource<Unit> =
        resourceOf { mealEntryDao.update(entry.toEntity()) }

    override suspend fun deleteMealEntry(id: Long): Resource<Unit> =
        resourceOf { mealEntryDao.deleteById(id) }
}
