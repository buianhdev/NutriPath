package vn.anhbt.nutripath.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.anhbt.nutripath.data.local.entity.NutritionGoalEntity

@Dao
interface NutritionGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: NutritionGoalEntity): Long

    @Update
    suspend fun update(goal: NutritionGoalEntity)

    @Delete
    suspend fun delete(goal: NutritionGoalEntity)

    @Query("DELETE FROM nutrition_goal WHERE id = :goalId")
    suspend fun deleteById(goalId: Long)

    @Query("SELECT * FROM nutrition_goal WHERE id = :goalId")
    suspend fun getById(goalId: Long): NutritionGoalEntity?

    @Query("SELECT * FROM nutrition_goal WHERE userId = :userId AND isActive = 1 LIMIT 1")
    suspend fun getActiveGoal(userId: Long): NutritionGoalEntity?

    @Query("SELECT * FROM nutrition_goal WHERE userId = :userId AND isActive = 1 LIMIT 1")
    fun observeActiveGoal(userId: Long): Flow<NutritionGoalEntity?>

    @Query("SELECT * FROM nutrition_goal WHERE userId = :userId ORDER BY createdAt DESC")
    suspend fun getHistory(userId: Long): List<NutritionGoalEntity>

    @Query("UPDATE nutrition_goal SET isActive = 0 WHERE userId = :userId AND isActive = 1")
    suspend fun deactivateAll(userId: Long)
}
