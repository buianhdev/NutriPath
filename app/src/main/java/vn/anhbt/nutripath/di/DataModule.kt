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
import vn.anhbt.nutripath.data.repository.FoodRepositoryImpl
import vn.anhbt.nutripath.data.repository.MealEntryRepositoryImpl
import vn.anhbt.nutripath.data.repository.NutritionGoalRepositoryImpl
import vn.anhbt.nutripath.data.repository.NutritionPlanRepositoryImpl
import vn.anhbt.nutripath.data.repository.UserRepositoryImpl
import vn.anhbt.nutripath.domain.repository.FoodRepository
import vn.anhbt.nutripath.domain.repository.MealEntryRepository
import vn.anhbt.nutripath.domain.repository.NutritionGoalRepository
import vn.anhbt.nutripath.domain.repository.NutritionPlanRepository
import vn.anhbt.nutripath.domain.repository.UserRepository

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

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<FoodRepository> { FoodRepositoryImpl(get()) }
    single<MealEntryRepository> { MealEntryRepositoryImpl(get()) }
    single<NutritionGoalRepository> { NutritionGoalRepositoryImpl(get()) }
    single<NutritionPlanRepository> { NutritionPlanRepositoryImpl(get()) }
}