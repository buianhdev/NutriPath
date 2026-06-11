package vn.anhbt.nutripath.data.local.converters

import androidx.room.TypeConverter
import vn.anhbt.nutripath.domain.model.PAL

class PALConverter {

    @TypeConverter
    fun fromPAL(from: PAL): String {
        return from.name
    }

    @TypeConverter
    fun toPAL(from: String): PAL {
        return PAL.valueOf(from)
    }
}
