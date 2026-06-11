package vn.anhbt.nutripath.data.local.converters

import androidx.room.TypeConverter
import vn.anhbt.nutripath.domain.model.MealSource

class MealSourceConverter {
    @TypeConverter
    fun fromMealSource(from: MealSource): String {
        return from.name
    }

    @TypeConverter
    fun fromString(from: String): MealSource {
        return MealSource.valueOf(from)
    }
}