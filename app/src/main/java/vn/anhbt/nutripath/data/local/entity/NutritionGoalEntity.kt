package vn.anhbt.nutripath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.anhbt.nutripath.domain.model.GoalSpeed
import vn.anhbt.nutripath.domain.model.GoalType

@Entity(tableName = "nutrition_goal")
data class NutritionGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val targetWeightKg: Double,
    val startWeightKg: Double,
    val goalSpeed: GoalSpeed,
    val goalType: GoalType,
    val createdAt: Long,
    val isActive: Boolean,
    val userId: Long
)
