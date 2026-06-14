package vn.anhbt.nutripath.data.local.converters

import androidx.room.TypeConverter
import vn.anhbt.nutripath.domain.model.GoalType

class GoalTypeConverter {

    @TypeConverter
    fun fromGoalType(from: GoalType): String {
        return from.name
    }

    @TypeConverter
    fun toGoalType(from: String): GoalType {
        return GoalType.valueOf(from)
    }
}