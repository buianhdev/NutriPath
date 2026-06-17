package vn.anhbt.nutripath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme
import kotlin.math.roundToLong

/**
 * Number stepper with +/- buttons. Mirrors `Stepper` in the design's `ui.jsx`. Values are clamped to
 * [min]..[max] and rounded to 2 decimals; the displayed value drops the decimal when whole.
 */
@Composable
fun NpStepper(
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    min: Double = 0.0,
    max: Double = 999.0,
    step: Double = 1.0,
    unit: String? = null,
    big: Boolean = false,
) {
    val colors = NutriPathTheme.colors
    fun set(raw: Double) {
        val rounded = (raw * 100).roundToLong() / 100.0
        onValueChange(rounded.coerceIn(min, max))
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(if (big) 12.dp else 8.dp),
    ) {
        StepButton(icon = NpIcons.Minus, big = big, onClick = { set(value - step) })
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = value.npNumber(),
                style = if (big) {
                    NutriPathTheme.typography.statLarge.copy(fontSize = 38.sp)
                } else {
                    NutriPathTheme.typography.statMedium.copy(fontSize = 25.sp)
                },
                color = colors.ink,
            )
            if (unit != null) {
                Text(
                    text = " $unit",
                    style = NutriPathTheme.typography.body.copy(fontSize = 15.sp),
                    color = colors.inkFaint,
                )
            }
        }
        StepButton(icon = NpIcons.Plus, big = big, onClick = { set(value + step) })
    }
}

@Composable
private fun StepButton(icon: ImageVector, big: Boolean, onClick: () -> Unit) {
    val colors = NutriPathTheme.colors
    val shape = RoundedCornerShape(if (big) 14.dp else 12.dp)
    val interaction = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(if (big) 48.dp else 38.dp)
            .pressScale(interaction)
            .clip(shape)
            .background(colors.surface2)
            .border(1.5.dp, colors.line, shape)
            .clickable(interactionSource = interaction, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = colors.ink, modifier = Modifier.size(if (big) 20.dp else 18.dp))
    }
}
