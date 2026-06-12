package vn.anhbt.nutripath.domain.model

import java.time.Instant

data class NutritionGoal(
    val id: Long,
    val goalType: GoalType,
    val goalSpeed: GoalSpeed,
    val targetWeightKg: Double,
    val startWeightKg: Double,
    val createdAt: Instant,
    val isActive: Boolean
)
