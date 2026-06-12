package vn.anhbt.nutripath.data.mapper

import vn.anhbt.nutripath.data.local.entity.FoodEntity
import vn.anhbt.nutripath.domain.model.Food

fun FoodEntity.toDomain(): Food = Food(
    id = id,
    name = name,
    ingredients = ingredients,
    baseAmountG = baseAmountG,
    caloriesPerBase = caloriesPerBase,
    proteinPerBase = proteinPerBase,
    carbPerBase = carbPerBase,
    fatPerBase = fatPerBase,
)

fun Food.toEntity(): FoodEntity = FoodEntity(
    id = id,
    name = name,
    ingredients = ingredients,
    baseAmountG = baseAmountG,
    caloriesPerBase = caloriesPerBase,
    proteinPerBase = proteinPerBase,
    carbPerBase = carbPerBase,
    fatPerBase = fatPerBase,
)
