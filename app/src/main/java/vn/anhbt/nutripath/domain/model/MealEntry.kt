package vn.anhbt.nutripath.domain.model

import java.time.Instant
import java.time.LocalDate

data class MealEntry(
    val id: String,
    val source: MealSource,
    val foodId: String?,
    val amountG: Double?,
    val macroBreakdown: MacroBreakdown,
    val createdAt: Instant,
    val updatedAt: Instant
)
