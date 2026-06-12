package vn.anhbt.nutripath.domain.model

import java.time.Instant

data class UserProfile(
    val id: Long,
    val weightKg: Double,
    val heightCm: Double,
    val age: Int,
    val gender: Gender,
    val pal: PAL,
    val createdAt: Instant,
    val updatedAt: Instant
)
