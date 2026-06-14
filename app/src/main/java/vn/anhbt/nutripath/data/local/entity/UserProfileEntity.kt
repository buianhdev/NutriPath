package vn.anhbt.nutripath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.anhbt.nutripath.domain.model.Gender
import vn.anhbt.nutripath.domain.model.PAL

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val weightKg: Double,
    val heightCm: Double,
    val age: Int,
    val pal: PAL,
    val gender: Gender,
    val createdAt: Long,
    val updatedAt: Long
)
