package vn.anhbt.nutripath.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * NutriPath color tokens.
 *
 * The palette is a fixed warm-oat / persimmon brand identity (see the design handoff). It is NOT
 * derived from Material You dynamic color — every semantic token below is brand-defined, including
 * the nutrition macro colors and the swappable [accent].
 *
 * Light + dark token sets mirror `NP_LIGHT` / `NP_DARK` in the design's `core.jsx`. The [accent] is
 * injected from [AccentColor]; [accentInk] (text/icons drawn on the accent) is theme-level: white in
 * light, near-black espresso in dark.
 */
@Immutable
data class NutriPathColors(
    /** App background — warm oat cream. */
    val bg: Color,
    /** Cards, sheets, nav bar. */
    val surface: Color,
    /** Inset fields, secondary tiles, segmented track. */
    val surface2: Color,
    /** Primary text — warm espresso. */
    val ink: Color,
    /** Secondary text / labels. */
    val inkSoft: Color,
    /** Tertiary text / units. */
    val inkFaint: Color,
    /** Borders. */
    val line: Color,
    /** Inner dividers. */
    val lineSoft: Color,
    /** Primary brand color — buttons, ring, active states. */
    val accent: Color,
    /** Text/icons drawn on [accent]. */
    val accentInk: Color,
    /** On-track / success. */
    val good: Color,
    /** Success card background. */
    val goodBg: Color,
    /** Macro: protein (berry). Also used as the "over budget" calorie color. */
    val protein: Color,
    /** Macro: carbs (honey). */
    val carb: Color,
    /** Macro: fat (blueberry). */
    val fat: Color,
    /** Unfilled progress-ring / bar track. */
    val ringTrack: Color,
    /** Bottom-sheet backdrop scrim. */
    val scrim: Color,
    /** Warm-tinted shadow base color (see [Modifier.softShadow]). */
    val shadowTint: Color,
    val isDark: Boolean,
)

/**
 * User-selectable accent ("Tweak"). Only the accent hue changes; [NutriPathColors.accentInk] stays
 * theme-level. Persimmon is the default brand accent.
 */
enum class AccentColor(val value: Color) {
    Persimmon(Color(0xFFEE5A2E)),
    Berry(Color(0xFFE1456A)),
    Avocado(Color(0xFF4E8E3F)),
    Blueberry(Color(0xFF5E6CC9)),
}

fun lightNutriPathColors(accent: AccentColor = AccentColor.Persimmon): NutriPathColors =
    NutriPathColors(
        bg = Color(0xFFF6ECDF),
        surface = Color(0xFFFFFFFF),
        surface2 = Color(0xFFFBF3E9),
        ink = Color(0xFF2A1E16),
        inkSoft = Color(0xFF7A6B5E),
        inkFaint = Color(0xFFAC9C8C),
        line = Color(0xFFECDFCF),
        lineSoft = Color(0xFFF2E8DB),
        accent = accent.value,
        accentInk = Color(0xFFFFFFFF),
        good = Color(0xFF3E8E5A),
        goodBg = Color(0xFFE4F2E7),
        protein = Color(0xFFE14B6A),
        carb = Color(0xFFE8902B),
        fat = Color(0xFF5E7FD0),
        ringTrack = Color(0xFFEFE3D4),
        scrim = Color(0x73140C06),
        shadowTint = Color(0xFF4A2E18),
        isDark = false,
    )

fun darkNutriPathColors(accent: AccentColor = AccentColor.Persimmon): NutriPathColors =
    NutriPathColors(
        bg = Color(0xFF161009),
        surface = Color(0xFF241B12),
        surface2 = Color(0xFF2E2317),
        ink = Color(0xFFF6ECE0),
        inkSoft = Color(0xFFC2B09C),
        inkFaint = Color(0xFF80715F),
        line = Color(0xFF3A2C1C),
        // Spec lists #31251800 (alpha 00 / invisible); kept faintly opaque so dividers still read.
        lineSoft = Color(0xFF312518),
        accent = accent.value,
        accentInk = Color(0xFF1A1209),
        good = Color(0xFF67C088),
        goodBg = Color(0xFF1E3326),
        protein = Color(0xFFF0708B),
        carb = Color(0xFFF2A23C),
        fat = Color(0xFF8AA4E8),
        ringTrack = Color(0xFF3A2C1C),
        scrim = Color(0x73000000),
        shadowTint = Color(0xFF000000),
        isDark = true,
    )
