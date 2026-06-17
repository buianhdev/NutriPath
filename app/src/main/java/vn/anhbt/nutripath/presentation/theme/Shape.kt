package vn.anhbt.nutripath.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Shared corner shapes. [card] follows the active [NutriPathDimens.cardRadius]; the rest are fixed
 * by the spec (sheets 30 top corners, buttons/pills full, inputs/segmented 14–16, small tiles 11–15).
 */
@Immutable
data class NutriPathShapes(
    /** Cards (radius scales with density). */
    val card: Shape,
    /** Bottom sheets — only the top corners are rounded. */
    val sheet: Shape,
    /** Fully-rounded pill (chips, status pills). */
    val pill: Shape,
    /** Buttons — fully rounded. */
    val button: Shape,
    /** Text inputs. */
    val input: Shape,
    /** Segmented control track + segments. */
    val segmented: Shape,
    /** Small square tiles (emoji thumbnails, icon tiles). */
    val tile: Shape,
)

fun nutriPathShapes(dimens: NutriPathDimens): NutriPathShapes = NutriPathShapes(
    card = RoundedCornerShape(dimens.cardRadius),
    sheet = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    pill = RoundedCornerShape(percent = 50),
    button = RoundedCornerShape(percent = 50),
    input = RoundedCornerShape(14.dp),
    segmented = RoundedCornerShape(16.dp),
    tile = RoundedCornerShape(13.dp),
)
