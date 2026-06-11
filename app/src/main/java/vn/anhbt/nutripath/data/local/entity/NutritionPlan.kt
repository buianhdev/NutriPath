package vn.anhbt.nutripath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_plan")
data class NutritionPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val bmr: Double,
    val tdee: Double,
    val goalCalories: Double,
    val proteinG: Double,
    val fatG: Double,
    val carbG: Double,
    val estimateWeightChangePerWeek: Double,
    val currentWeightKg: Double,
    val targetWeightKg: Double,
    val estimatedGoalWeeks: Double?,
    val createdAt: Long,
    val nutritionGoalId: Long
)