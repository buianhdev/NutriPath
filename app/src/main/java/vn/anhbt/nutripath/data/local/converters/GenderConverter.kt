package vn.anhbt.nutripath.data.local.converters

import androidx.room.TypeConverter
import vn.anhbt.nutripath.domain.model.Gender

class GenderConverter {

    @TypeConverter
    fun fromGender(from: Gender): String {
        return from.name
    }

    @TypeConverter
    fun toGender(from: String): Gender {
        return Gender.valueOf(from)
    }
}