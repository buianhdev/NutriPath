package vn.anhbt.nutripath.data.mapper

import java.time.Instant
import vn.anhbt.nutripath.data.local.entity.UserProfileEntity
import vn.anhbt.nutripath.domain.model.UserProfile

fun UserProfileEntity.toDomain(): UserProfile = UserProfile(
    id = id,
    weightKg = weightKg,
    heightCm = heightCm,
    age = age,
    gender = gender,
    pal = pal,
    createdAt = Instant.ofEpochMilli(createdAt),
    updatedAt = Instant.ofEpochMilli(updatedAt),
)

fun UserProfile.toEntity(): UserProfileEntity = UserProfileEntity(
    id = id,
    weightKg = weightKg,
    heightCm = heightCm,
    age = age,
    pal = pal,
    gender = gender,
    createdAt = createdAt.toEpochMilli(),
    updatedAt = updatedAt.toEpochMilli(),
)
