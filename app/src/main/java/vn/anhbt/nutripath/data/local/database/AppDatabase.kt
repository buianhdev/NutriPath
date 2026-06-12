package vn.anhbt.nutripath.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.anhbt.nutripath.data.local.converters.GenderConverter
import vn.anhbt.nutripath.data.local.converters.GoalSpeedConverter
import vn.anhbt.nutripath.data.local.converters.GoalTypeConverter
import vn.anhbt.nutripath.data.local.converters.MealSourceConverter
import vn.anhbt.nutripath.data.local.converters.PALConverter
import vn.anhbt.nutripath.data.local.dao.FoodDao
import vn.anhbt.nutripath.data.local.dao.MealEntryDao
import vn.anhbt.nutripath.data.local.dao.NutritionGoalDao
import vn.anhbt.nutripath.data.local.dao.NutritionPlanDao
import vn.anhbt.nutripath.data.local.dao.UserDao
import vn.anhbt.nutripath.data.local.entity.FoodEntity
import vn.anhbt.nutripath.data.local.entity.MealEntryEntity
import vn.anhbt.nutripath.data.local.entity.NutritionGoalEntity
import vn.anhbt.nutripath.data.local.entity.NutritionPlanEntity
import vn.anhbt.nutripath.data.local.entity.UserProfileEntity

@Database(entities = [
    FoodEntity::class,
    MealEntryEntity::class,
    NutritionGoalEntity::class,
    NutritionPlanEntity::class,
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
abstract class AppDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun mealEntryDao(): MealEntryDao
    abstract fun nutritionGoalDao(): NutritionGoalDao
    abstract fun nutritionPlanDao(): NutritionPlanDao
    abstract fun userDao(): UserDao

}