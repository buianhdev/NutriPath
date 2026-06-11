package vn.anhbt.nutripath.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import vn.anhbt.nutripath.domain.model.GoalSpeed
import vn.anhbt.nutripath.domain.model.GoalType

@Entity(
    tableName = "nutrition_goal",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId")
    ]
)
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
