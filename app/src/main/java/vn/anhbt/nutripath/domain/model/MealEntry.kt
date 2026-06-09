package vn.anhbt.nutripath.domain.model

import java.time.Instant
import java.time.LocalDate

data class MealEntry(
    val id: String,
    val source: MealSource,
    val date: LocalDate,
    val foodId: String?,
    val foodName: String,
    val amountG: Double?,
    val macroBreakdown: MacroBreakdown,
    val createdAt: Instant,
    val updatedAt: Instant
)
