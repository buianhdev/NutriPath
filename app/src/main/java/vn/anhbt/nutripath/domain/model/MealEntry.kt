package vn.anhbt.nutripath.domain.model

import java.time.Instant

data class MealEntry(
    val id: Long,
    val userId: Long,
    val source: MealSource,
    val foodId: Long?,
    val amountG: Double?,
    val macroBreakdown: MacroBreakdown,
    val createdAt: Instant,
    val updatedAt: Instant
)
