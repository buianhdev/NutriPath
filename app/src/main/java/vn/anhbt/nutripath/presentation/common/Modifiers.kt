package vn.anhbt.nutripath.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import java.util.Locale
import kotlin.math.floor

/** Press-to-scale feedback (~0.97) used by buttons, segments, and chips. */
@Composable
internal fun Modifier.pressScale(
    interactionSource: MutableInteractionSource,
    pressedScale: Float = 0.97f,
): Modifier {
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) pressedScale else 1f,
        label = "pressScale",
    )
    return graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

/** Formats a number with no decimals when whole, otherwise one decimal (e.g. 70 / 70.5). */
internal fun Double.npNumber(): String =
    if (this == floor(this)) toLong().toString() else String.format(Locale.US, "%.1f", this)
