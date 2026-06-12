package vn.anhbt.nutripath.data.mapper

import java.time.Instant
import vn.anhbt.nutripath.data.local.entity.NutritionPlanEntity
import vn.anhbt.nutripath.domain.model.NutritionPlan

fun NutritionPlanEntity.toDomain(): NutritionPlan = NutritionPlan(
    id = id,
    bmr = bmr,
    tdee = tdee,
    goalCalories = goalCalories,
    proteinG = proteinG,
    fatG = fatG,
    carbG = carbG,
    estimatedWeightChangePerWeek = estimatedWeightChangePerWeek,
    currentWeightKg = currentWeightKg,
    targetWeightKg = targetWeightKg,
    estimatedGoalWeeks = estimatedGoalWeeks,
    profileSnapshot = profileSnapshot,
    goalSnapshot = goalSnapshot,
    createdAt = Instant.ofEpochMilli(createdAt),
    isActive = isActive,
)

fun NutritionPlan.toEntity(nutritionGoalId: Long): NutritionPlanEntity = NutritionPlanEntity(
    id = id,
    bmr = bmr,
    tdee = tdee,
    goalCalories = goalCalories,
    proteinG = proteinG,
    fatG = fatG,
    carbG = carbG,
    estimatedWeightChangePerWeek = estimatedWeightChangePerWeek,
    currentWeightKg = currentWeightKg,
    targetWeightKg = targetWeightKg,
    estimatedGoalWeeks = estimatedGoalWeeks,
    profileSnapshot = profileSnapshot,
    goalSnapshot = goalSnapshot,
    createdAt = createdAt.toEpochMilli(),
    isActive = isActive,
    nutritionGoalId = nutritionGoalId,
)
