package vn.anhbt.nutripath.domain.model

enum class PAL(val factor: Double) {
    SEDENTARY(1.2),
    LIGHTLY_ACTIVE(1.375),
    MODERATELY_ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    EXTRA_ACTIVE(1.9)
}