package vn.anhbt.nutripath.domain.model

import java.time.Instant

data class NutritionPlan(
    val id: String,
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
    val profileSnapshot: ProfileSnapshot,
    val goalSnapshot: GoalSnapshot,
    val createdAt: Instant,
    val isActive: Boolean
) {
    data class ProfileSnapshot(
        val weightKg: Double,
        val heightCm: Double,
        val age: Int,
        val gender: Gender,
        val pal: PAL
    )

    data class GoalSnapshot(
        val goalType: GoalType,
        val goalSpeed: GoalSpeed,
        val targetWeightKg: Double,
        val startWeightKg: Double
    )
}
