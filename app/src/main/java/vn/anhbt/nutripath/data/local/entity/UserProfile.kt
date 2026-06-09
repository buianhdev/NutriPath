package vn.anhbt.nutripath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.anhbt.nutripath.domain.model.PAL

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val weightKg: Double,
    val heightCm: Double,
    val age: Int,
    val pal: PAL
)
