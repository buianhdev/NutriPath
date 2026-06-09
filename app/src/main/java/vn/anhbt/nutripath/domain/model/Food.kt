package vn.anhbt.nutripath.domain.model

data class Food(
    val id: String,
    val name: String,
    val ingredients: List<String>,
    val baseAmountG: Double,
    val caloriesPerBase: Double,
    val proteinPerBase: Double,
    val carbPerBase: Double,
    val fatPerBase: Double
)
