package vn.anhbt.nutripath.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import vn.anhbt.nutripath.domain.model.NutritionPlan

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
    @Embedded(prefix = "profile_") val profileSnapshot: NutritionPlan.ProfileSnapshot,
    @Embedded(prefix = "goal_") val goalSnapshot: NutritionPlan.GoalSnapshot,
    val createdAt: Long,
    val isActive: Boolean,
    val nutritionGoalId: Long
)
