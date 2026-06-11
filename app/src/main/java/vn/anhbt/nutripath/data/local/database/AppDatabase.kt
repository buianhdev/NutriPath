package vn.anhbt.nutripath.data.local.database

import androidx.room.Database
import androidx.room.TypeConverters
import vn.anhbt.nutripath.data.local.converters.GenderConverter
import vn.anhbt.nutripath.data.local.converters.GoalSpeedConverter
import vn.anhbt.nutripath.data.local.converters.GoalTypeConverter
import vn.anhbt.nutripath.data.local.converters.MealSourceConverter
import vn.anhbt.nutripath.data.local.converters.PALConverter
import vn.anhbt.nutripath.data.local.entity.FoodEntity
import vn.anhbt.nutripath.data.local.entity.MealEntryEntity
import vn.anhbt.nutripath.data.local.entity.NutritionGoalEntity
import vn.anhbt.nutripath.data.local.entity.NutritionPlan
import vn.anhbt.nutripath.data.local.entity.UserProfileEntity

@Database(entities = [
    FoodEntity::class,
    MealEntryEntity::class,
    NutritionGoalEntity::class,
    NutritionPlan::class,
    UserProfileEntity::class
],
    version = 1)
@TypeConverters(
    GenderConverter::class,
    GoalSpeedConverter::class,
    GoalTypeConverter::class,
    MealSourceConverter::class,
    PALConverter::class
)
abstract class AppDatabase {



}