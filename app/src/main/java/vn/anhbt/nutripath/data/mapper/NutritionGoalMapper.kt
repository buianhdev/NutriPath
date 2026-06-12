package vn.anhbt.nutripath.data.mapper

import java.time.Instant
import vn.anhbt.nutripath.data.local.entity.NutritionGoalEntity
import vn.anhbt.nutripath.domain.model.NutritionGoal

fun NutritionGoalEntity.toDomain(): NutritionGoal = NutritionGoal(
    id = id,
    goalType = goalType,
    goalSpeed = goalSpeed,
    targetWeightKg = targetWeightKg,
    startWeightKg = startWeightKg,
    createdAt = Instant.ofEpochMilli(createdAt),
    isActive = isActive,
)

fun NutritionGoal.toEntity(userId: Long): NutritionGoalEntity = NutritionGoalEntity(
    id = id,
    targetWeightKg = targetWeightKg,
    startWeightKg = startWeightKg,
    goalSpeed = goalSpeed,
    goalType = goalType,
    createdAt = createdAt.toEpochMilli(),
    isActive = isActive,
    userId = userId,
)
