package vn.anhbt.nutripath.data.repository

import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.common.resourceOf
import vn.anhbt.nutripath.core.util.toInstantRange
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

    override suspend fun getMealEntriesByDate(userId: Long, date: LocalDate): Resource<List<MealEntry>> {
        val (startMillis, endMillis) = date.toEpochMillisRange()
        return resourceOf {
            mealEntryDao.getByDateRange(userId, startMillis, endMillis).map { it.toDomain() }
        }
    }

    override fun observeMealEntriesByDate(userId: Long, date: LocalDate): Flow<List<MealEntry>> {
        val (startMillis, endMillis) = date.toEpochMillisRange()
        return mealEntryDao.observeByDateRange(userId, startMillis, endMillis)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun saveMealEntry(entry: MealEntry): Resource<Long> =
        resourceOf { mealEntryDao.insert(entry.toEntity()) }

    override suspend fun updateMealEntry(entry: MealEntry): Resource<Unit> =
        resourceOf { mealEntryDao.update(entry.toEntity()) }

    override suspend fun deleteMealEntry(id: Long): Resource<Unit> =
        resourceOf { mealEntryDao.deleteById(id) }

    private fun LocalDate.toEpochMillisRange(): Pair<Long, Long> {
        val (start, endExclusive) = toInstantRange()
        return start.toEpochMilli() to (endExclusive.toEpochMilli() - 1)
    }
}
