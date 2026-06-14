package vn.anhbt.nutripath.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "nutrition_plan",
    foreignKeys = [
        ForeignKey(
            entity = NutritionGoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["nutritionGoalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("nutritionGoalId")
    ]
)
data class NutritionPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val bmr: Double,
    val tdee: Double,
    val goalCalories: Double,
    val proteinG: Double,
    val fatG: Double,
    val carbG: Double,
    val estimatedWeightChangePerWeek: Double,
    val currentWeightKg: Double,
    val targetWeightKg: Double,
    val estimatedGoalWeeks: Double?,
    val createdAt: Long,
    val isActive: Boolean,
    val nutritionGoalId: Long
)
