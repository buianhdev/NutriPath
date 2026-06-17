package vn.anhbt.nutripath.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalNutriPathColors = staticCompositionLocalOf { lightNutriPathColors() }
val LocalNutriPathDimens = staticCompositionLocalOf { RegularDimens }
val LocalNutriPathShapes = staticCompositionLocalOf { nutriPathShapes(RegularDimens) }
val LocalNutriPathTypography = staticCompositionLocalOf { nutriPathTypography }

/**
 * Root theme for the app. Provides the NutriPath design tokens via [CompositionLocal]s and wraps
 * [MaterialTheme] underneath (mapped to the brand palette) so stock Material widgets, ripples, and
 * text defaults stay on-brand.
 *
 * Dynamic color is intentionally NOT used — the brand palette is fixed. [accent] and [density] are
 * the user-selectable "Tweaks"; both default to the brand defaults.
 */
@Composable
fun NutriPathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accent: AccentColor = AccentColor.Persimmon,
    density: NpDensity = NpDensity.Regular,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) darkNutriPathColors(accent) else lightNutriPathColors(accent)
    val dimens = dimensFor(density)
    val shapes = nutriPathShapes(dimens)

    CompositionLocalProvider(
        LocalNutriPathColors provides colors,
        LocalNutriPathDimens provides dimens,
        LocalNutriPathShapes provides shapes,
        LocalNutriPathTypography provides nutriPathTypography,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterialColorScheme(),
            typography = MaterialTypography,
            content = content,
        )
    }
}

/** Accessor for NutriPath design tokens, à la [MaterialTheme]. */
object NutriPathTheme {
    val colors: NutriPathColors
        @Composable @ReadOnlyComposable get() = LocalNutriPathColors.current

    val dimens: NutriPathDimens
        @Composable @ReadOnlyComposable get() = LocalNutriPathDimens.current

    val shapes: NutriPathShapes
        @Composable @ReadOnlyComposable get() = LocalNutriPathShapes.current

    val typography: NutriPathTypography
        @Composable @ReadOnlyComposable get() = LocalNutriPathTypography.current
}

/** Maps the brand tokens onto a Material3 [ColorScheme] so Material components stay on-brand. */
private fun NutriPathColors.toMaterialColorScheme(): ColorScheme {
    val base = if (isDark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = accent,
        onPrimary = accentInk,
        secondary = accent,
        onSecondary = accentInk,
        tertiary = accent,
        onTertiary = accentInk,
        background = bg,
        onBackground = ink,
        surface = surface,
        onSurface = ink,
        surfaceVariant = surface2,
        onSurfaceVariant = inkSoft,
        outline = line,
        outlineVariant = lineSoft,
        error = protein,
        onError = Color.White,
        scrim = scrim,
    )
}
