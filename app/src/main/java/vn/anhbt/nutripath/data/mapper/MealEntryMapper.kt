package vn.anhbt.nutripath.data.mapper

import java.time.Instant
import vn.anhbt.nutripath.data.local.entity.MealEntryEntity
import vn.anhbt.nutripath.domain.model.MealEntry

fun MealEntryEntity.toDomain(): MealEntry = MealEntry(
    id = id,
    source = source,
    foodId = foodId,
    amountG = amountG,
    macroBreakdown = macroBreakdown,
    createdAt = Instant.ofEpochMilli(createdAt),
    updatedAt = Instant.ofEpochMilli(updatedAt),
)

fun MealEntry.toEntity(): MealEntryEntity = MealEntryEntity(
    id = id,
    source = source,
    foodId = foodId,
    amountG = amountG,
    macroBreakdown = macroBreakdown,
    createdAt = createdAt.toEpochMilli(),
    updatedAt = updatedAt.toEpochMilli(),
)
