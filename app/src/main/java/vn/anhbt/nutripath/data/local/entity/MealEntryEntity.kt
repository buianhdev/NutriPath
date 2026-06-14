package vn.anhbt.nutripath.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import vn.anhbt.nutripath.domain.model.MacroBreakdown
import vn.anhbt.nutripath.domain.model.MealSource

@Entity(
    tableName = "meal_entry",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("userId"),
        Index("foodId"),
        Index("createdAt")
    ]
)
data class MealEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val userId: Long,
    val source: MealSource,
    val foodId: Long?,
    val amountG: Double?,
    @Embedded val macroBreakdown: MacroBreakdown,
    val createdAt: Long,
    val updatedAt: Long
)
