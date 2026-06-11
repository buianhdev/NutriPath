package vn.anhbt.nutripath.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithNutritionGoals(
    @Embedded val user: UserProfileEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val nutritionGoalEntities: List<NutritionGoalEntity>
)