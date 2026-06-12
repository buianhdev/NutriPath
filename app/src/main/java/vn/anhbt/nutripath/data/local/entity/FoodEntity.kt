package vn.anhbt.nutripath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val baseAmountG: Double,
    val caloriesPerBase: Double,
    val proteinPerBase: Double,
    val carbPerBase: Double,
    val fatPerBase: Double
)