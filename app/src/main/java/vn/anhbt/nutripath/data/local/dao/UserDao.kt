package vn.anhbt.nutripath.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.anhbt.nutripath.data.local.entity.UserProfileEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userProfile: UserProfileEntity): Long

    @Update
    suspend fun update(userProfile: UserProfileEntity)

    @Delete
    suspend fun delete(userProfile: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE id = :userId")
    suspend fun getUserProfile(userId: Long): UserProfileEntity?

    @Query("DELETE FROM user_profile WHERE id = :userId")
    suspend fun deleteById(userId: Long)

    @Query("SELECT * FROM user_profile ORDER BY updatedAt DESC LIMIT 1")
    suspend fun getCurrentUserProfile(): UserProfileEntity?

    @Query("SELECT * FROM user_profile ORDER BY updatedAt DESC LIMIT 1")
    fun observeCurrentUserProfile(): Flow<UserProfileEntity?>
}
