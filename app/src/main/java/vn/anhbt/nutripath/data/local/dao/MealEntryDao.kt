package vn.anhbt.nutripath.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.anhbt.nutripath.data.local.entity.MealEntryEntity

@Dao
interface MealEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mealEntry: MealEntryEntity): Long

    @Update
    suspend fun update(mealEntry: MealEntryEntity)

    @Delete
    suspend fun delete(mealEntry: MealEntryEntity)

    @Query("DELETE FROM meal_entry WHERE id = :mealEntryId")
    suspend fun deleteById(mealEntryId: Long)

    @Query("SELECT * FROM meal_entry WHERE id = :mealEntryId")
    suspend fun getById(mealEntryId: Long): MealEntryEntity?

    @Query("SELECT * FROM meal_entry WHERE userId = :userId AND createdAt BETWEEN :startMillis AND :endMillis ORDER BY createdAt ASC")
    suspend fun getByDateRange(userId: Long, startMillis: Long, endMillis: Long): List<MealEntryEntity>

    @Query("SELECT * FROM meal_entry WHERE userId = :userId AND createdAt BETWEEN :startMillis AND :endMillis ORDER BY createdAt ASC")
    fun observeByDateRange(userId: Long, startMillis: Long, endMillis: Long): Flow<List<MealEntryEntity>>
}
