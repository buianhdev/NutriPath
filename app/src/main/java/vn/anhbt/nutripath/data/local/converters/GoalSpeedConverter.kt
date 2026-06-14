package vn.anhbt.nutripath.data.local.converters

import androidx.room.TypeConverter
import vn.anhbt.nutripath.domain.model.GoalSpeed

class GoalSpeedConverter {

    @TypeConverter
    fun fromGoalSpeed(from: GoalSpeed): String {
        return from.name
    }

    @TypeConverter
    fun toGoalSpeed(from: String): GoalSpeed {
        return GoalSpeed.valueOf(from)
    }
}