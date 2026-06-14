package vn.anhbt.nutripath.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.anhbt.nutripath.data.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FoodEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodEntity>): List<Long>

    @Update
    suspend fun update(food: FoodEntity)

    @Delete
    suspend fun delete(food: FoodEntity)

    @Query("DELETE FROM food WHERE id = :foodId")
    suspend fun deleteById(foodId: Long)

    @Query("SELECT * FROM food WHERE id = :foodId")
    suspend fun getById(foodId: Long): FoodEntity?

    @Query("SELECT * FROM food ORDER BY name ASC")
    suspend fun getAll(): List<FoodEntity>

    @Query("SELECT * FROM food ORDER BY name ASC")
    fun observeAll(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food WHERE LOWER(name) LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun search(query: String): List<FoodEntity>
}
