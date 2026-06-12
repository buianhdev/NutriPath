package vn.anhbt.nutripath.data.local.converters

import androidx.room.TypeConverter

private const val DELIMITER = "\u001F"

class StringListConverter {
    @TypeConverter
    fun fromList(value: List<String>): String = value.joinToString(DELIMITER)

    @TypeConverter
    fun toList(value: String): List<String> =
        if (value.isEmpty()) emptyList() else value.split(DELIMITER)
}
