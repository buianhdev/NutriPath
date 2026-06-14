package vn.anhbt.nutripath.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NutritionGoalAndPlan(
    @Embedded val nutritionGoalEntity: NutritionGoalEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "nutritionGoalId"
    )
    val nutritionPlan: NutritionPlanEntity
)
