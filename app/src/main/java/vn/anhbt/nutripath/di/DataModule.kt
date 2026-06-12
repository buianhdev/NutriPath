package vn.anhbt.nutripath.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import vn.anhbt.nutripath.data.local.dao.FoodDao
import vn.anhbt.nutripath.data.local.dao.MealEntryDao
import vn.anhbt.nutripath.data.local.dao.NutritionGoalDao
import vn.anhbt.nutripath.data.local.dao.NutritionPlanDao
import vn.anhbt.nutripath.data.local.dao.UserDao
import vn.anhbt.nutripath.data.local.database.AppDatabase

val dataModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "nutripath.db"
        ).build()
    }

    single<FoodDao> {
        get<AppDatabase>().foodDao()
    }

    single<UserDao> {
        get<AppDatabase>().userDao()
    }

    single<MealEntryDao> {
        get<AppDatabase>().mealEntryDao()
    }

    single<NutritionGoalDao> {
        get<AppDatabase>().nutritionGoalDao()
    }

    single<NutritionPlanDao> {
        get<AppDatabase>().nutritionPlanDao()
    }
}