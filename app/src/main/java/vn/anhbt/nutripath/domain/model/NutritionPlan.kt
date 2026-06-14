package vn.anhbt.nutripath.domain.model

import java.time.Instant

data class NutritionPlan(
    val id: Long = 0,
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
    val nutritionGoalId: Long,
    val createdAt: Instant,
    val isActive: Boolean
)