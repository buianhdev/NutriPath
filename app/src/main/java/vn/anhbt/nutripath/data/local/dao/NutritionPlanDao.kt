package vn.anhbt.nutripath.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.anhbt.nutripath.data.local.entity.NutritionPlanEntity

@Dao
interface NutritionPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: NutritionPlanEntity): Long

    @Update
    suspend fun update(plan: NutritionPlanEntity)

    @Delete
    suspend fun delete(plan: NutritionPlanEntity)

    @Query("DELETE FROM nutrition_plan WHERE id = :planId")
    suspend fun deleteById(planId: Long)

    @Query("SELECT * FROM nutrition_plan WHERE id = :planId")
    suspend fun getById(planId: Long): NutritionPlanEntity?

    @Query("SELECT * FROM nutrition_plan WHERE nutritionGoalId = :goalId LIMIT 1")
    suspend fun getByGoalId(goalId: Long): NutritionPlanEntity?

    @Query(
        """
        SELECT p.* FROM nutrition_plan p
        INNER JOIN nutrition_goal g ON g.id = p.nutritionGoalId
        WHERE g.userId = :userId AND g.isActive = 1
        LIMIT 1
        """
    )
    suspend fun getActivePlan(userId: Long): NutritionPlanEntity?

    @Query(
        """
        SELECT p.* FROM nutrition_plan p
        INNER JOIN nutrition_goal g ON g.id = p.nutritionGoalId
        WHERE g.userId = :userId AND g.isActive = 1
        LIMIT 1
        """
    )
    fun observeActivePlan(userId: Long): Flow<NutritionPlanEntity?>

    @Query(
        """
        SELECT p.* FROM nutrition_plan p
        INNER JOIN nutrition_goal g ON g.id = p.nutritionGoalId
        WHERE g.userId = :userId
        ORDER BY p.createdAt DESC
        """
    )
    suspend fun getHistory(userId: Long): List<NutritionPlanEntity>
}
