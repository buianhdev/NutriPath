package vn.anhbt.nutripath.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Warm-tinted card/button shadows from the spec.
 *
 * The handoff specifies layered CSS box-shadows; Compose elevation can't reproduce them 1:1, so
 * these approximate the look with elevation + the theme's warm [NutriPathColors.shadowTint] as the
 * ambient/spot color. Colored shadows render on API 28+ (minSdk is 26); on 26–27 they fall back to
 * the default black shadow, which is acceptable.
 */
@Composable
fun Modifier.softShadow(shape: Shape, clip: Boolean = false): Modifier {
    val tint = NutriPathTheme.colors.shadowTint
    return shadow(
        elevation = 8.dp,
        shape = shape,
        clip = clip,
        ambientColor = tint.copy(alpha = 0.06f),
        spotColor = tint.copy(alpha = 0.10f),
    )
}

@Composable
fun Modifier.standardShadow(shape: Shape, clip: Boolean = false): Modifier {
    val tint = NutriPathTheme.colors.shadowTint
    return shadow(
        elevation = 16.dp,
        shape = shape,
        clip = clip,
        ambientColor = tint.copy(alpha = 0.08f),
        spotColor = tint.copy(alpha = 0.14f),
    )
}

/** Accent-colored glow for the center FAB. */
@Composable
fun Modifier.fabGlow(shape: Shape, clip: Boolean = false): Modifier {
    val accent = NutriPathTheme.colors.accent
    return shadow(
        elevation = 14.dp,
        shape = shape,
        clip = clip,
        ambientColor = accent.copy(alpha = 0.40f),
        spotColor = accent.copy(alpha = 0.40f),
    )
}
