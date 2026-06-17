package vn.anhbt.nutripath.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Layout density "Tweak" (compact / regular / comfy). Mirrors `NP_DENSITY` in the design's
 * `core.jsx`. Regular is the default.
 */
enum class NpDensity { Compact, Regular, Comfy }

/**
 * Spacing / shape scale that changes with the selected [NpDensity]. Values not tied to density
 * (sheet radius, pill, input, etc.) live in [NutriPathShapes] instead.
 */
@Immutable
data class NutriPathDimens(
    /** Outer padding around a screen's content. */
    val screenPadding: Dp,
    /** Default gap between stacked elements (cards, rows). */
    val gap: Dp,
    /** Inner padding inside a [androidx.compose.foundation.layout.Box]-like card. */
    val cardPadding: Dp,
    /** Corner radius for cards. */
    val cardRadius: Dp,
    /** General element corner radius (tiles, insets). */
    val radius: Dp,
)

val CompactDimens = NutriPathDimens(
    screenPadding = 14.dp,
    gap = 10.dp,
    cardPadding = 14.dp,
    cardRadius = 20.dp,
    radius = 18.dp,
)

val RegularDimens = NutriPathDimens(
    screenPadding = 18.dp,
    gap = 14.dp,
    cardPadding = 18.dp,
    cardRadius = 24.dp,
    radius = 24.dp,
)

val ComfyDimens = NutriPathDimens(
    screenPadding = 22.dp,
    gap = 18.dp,
    cardPadding = 22.dp,
    cardRadius = 28.dp,
    radius = 28.dp,
)

fun dimensFor(density: NpDensity): NutriPathDimens = when (density) {
    NpDensity.Compact -> CompactDimens
    NpDensity.Regular -> RegularDimens
    NpDensity.Comfy -> ComfyDimens
}
