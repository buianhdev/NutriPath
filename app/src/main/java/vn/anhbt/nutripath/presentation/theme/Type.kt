package vn.anhbt.nutripath.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import vn.anhbt.nutripath.R

/**
 * Downloadable Google Fonts provider (Google Play services). The certificate array ships with the
 * `androidx.compose.ui:ui-text-google-fonts` dependency.
 */
private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

private val BricolageGrotesque = GoogleFont("Bricolage Grotesque")
private val PlusJakartaSans = GoogleFont("Plus Jakarta Sans")

/** Display / numbers — Bricolage Grotesque. Headings, screen titles, all large stat numbers. */
val DisplayFontFamily = FontFamily(
    Font(googleFont = BricolageGrotesque, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = BricolageGrotesque, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = BricolageGrotesque, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = BricolageGrotesque, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = BricolageGrotesque, fontProvider = provider, weight = FontWeight.ExtraBold),
)

/** Body / UI — Plus Jakarta Sans. */
val BodyFontFamily = FontFamily(
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.ExtraBold),
)

/**
 * Semantic type scale named after the design vocabulary (see the handoff §Typography). Stat numbers
 * vary in size on screen (30–60) — use the closest [statHero]/[statLarge]/[statMedium] and `.copy`
 * the `fontSize` where a specific value is required.
 */
@Immutable
data class NutriPathTypography(
    /** Welcome / brand title — 40 / ExtraBold. */
    val displayLarge: TextStyle,
    /** Screen titles — 28 / ExtraBold. */
    val screenTitle: TextStyle,
    /** Bottom-sheet titles — 21 / Bold. */
    val sheetTitle: TextStyle,
    /** Section headings — 18 / Bold. */
    val sectionHeading: TextStyle,
    /** Hero stat (big goal-calorie number) — ~56 / ExtraBold. */
    val statHero: TextStyle,
    /** Large stat — ~40 / ExtraBold. */
    val statLarge: TextStyle,
    /** Medium stat (card stat number) — ~30 / ExtraBold. */
    val statMedium: TextStyle,
    /** Body — 16 / SemiBold. */
    val body: TextStyle,
    /** Smaller body — 14 / SemiBold. */
    val bodySmall: TextStyle,
    /** Field labels — 13.5 / Bold. */
    val label: TextStyle,
    /** Captions — 12.5 / SemiBold. */
    val caption: TextStyle,
    /** Micro text / units — 11 / Bold. */
    val micro: TextStyle,
    /** Bottom-nav labels — 11 / Bold. */
    val navLabel: TextStyle,
    /** Button text — 15.5 / Bold. */
    val button: TextStyle,
    /** Uppercase kicker ("STEP n OF 4", "YOUR DAILY PLAN") — 12 / Bold, wide tracking. */
    val kicker: TextStyle,
)

val nutriPathTypography = NutriPathTypography(
    displayLarge = TextStyle(
        fontFamily = DisplayFontFamily, fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp, lineHeight = 44.sp, letterSpacing = (-0.025).em,
    ),
    screenTitle = TextStyle(
        fontFamily = DisplayFontFamily, fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp, lineHeight = 32.sp, letterSpacing = (-0.02).em,
    ),
    sheetTitle = TextStyle(
        fontFamily = DisplayFontFamily, fontWeight = FontWeight.Bold,
        fontSize = 21.sp, lineHeight = 26.sp, letterSpacing = (-0.01).em,
    ),
    sectionHeading = TextStyle(
        fontFamily = DisplayFontFamily, fontWeight = FontWeight.Bold,
        fontSize = 18.sp, lineHeight = 22.sp, letterSpacing = (-0.01).em,
    ),
    statHero = TextStyle(
        fontFamily = DisplayFontFamily, fontWeight = FontWeight.ExtraBold,
        fontSize = 56.sp, lineHeight = 58.sp, letterSpacing = (-0.02).em,
    ),
    statLarge = TextStyle(
        fontFamily = DisplayFontFamily, fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp, lineHeight = 42.sp, letterSpacing = (-0.02).em,
    ),
    statMedium = TextStyle(
        fontFamily = DisplayFontFamily, fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp, lineHeight = 32.sp, letterSpacing = (-0.01).em,
    ),
    body = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp, lineHeight = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp, lineHeight = 20.sp,
    ),
    label = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.Bold,
        fontSize = 13.5.sp, lineHeight = 18.sp, letterSpacing = 0.005.em,
    ),
    caption = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.SemiBold,
        fontSize = 12.5.sp, lineHeight = 16.sp,
    ),
    micro = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.Bold,
        fontSize = 11.sp, lineHeight = 14.sp,
    ),
    navLabel = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.Bold,
        fontSize = 11.sp, lineHeight = 13.sp,
    ),
    button = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.Bold,
        fontSize = 15.5.sp, lineHeight = 18.sp, letterSpacing = 0.005.em,
    ),
    kicker = TextStyle(
        fontFamily = BodyFontFamily, fontWeight = FontWeight.Bold,
        fontSize = 12.sp, lineHeight = 15.sp, letterSpacing = 0.06.em,
    ),
)

/**
 * Material3 [Typography] mapped from the NutriPath scale so stock Material components (default
 * `Text`, dialogs, etc.) inherit the brand fonts. App UI should prefer [NutriPathTypography].
 */
val MaterialTypography = Typography(
    displayLarge = nutriPathTypography.displayLarge,
    headlineMedium = nutriPathTypography.screenTitle,
    headlineSmall = nutriPathTypography.sectionHeading,
    titleLarge = nutriPathTypography.sheetTitle,
    titleMedium = nutriPathTypography.body,
    bodyLarge = nutriPathTypography.body,
    bodyMedium = nutriPathTypography.bodySmall,
    bodySmall = nutriPathTypography.caption,
    labelLarge = nutriPathTypography.button,
    labelMedium = nutriPathTypography.label,
    labelSmall = nutriPathTypography.micro,
)
