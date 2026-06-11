package vn.anhbt.nutripath.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import vn.anhbt.nutripath.domain.model.MacroBreakdown

@Entity(
    tableName = "meal_entry",
    foreignKeys = [
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("foodId")
    ]
)
data class MealEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val amountG: Double,
    @Embedded val macroBreakdown: MacroBreakdown,
    val createdAt: Long,
    val foodId: Long
)
