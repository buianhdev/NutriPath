package vn.anhbt.nutripath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme
import vn.anhbt.nutripath.presentation.theme.softShadow

enum class NpButtonVariant { Primary, Soft, Ghost, Danger }

enum class NpButtonSize { Sm, Md, Lg }

/**
 * Pill button. Mirrors `Btn` in the design's `ui.jsx`: four variants, three sizes, optional leading
 * icon, full-width option, and a press-to-scale interaction (no ripple).
 */
@Composable
fun NpButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: NpButtonVariant = NpButtonVariant.Primary,
    size: NpButtonSize = NpButtonSize.Md,
    fullWidth: Boolean = false,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val colors = NutriPathTheme.colors
    val shape = NutriPathTheme.shapes.button
    val interaction = remember { MutableInteractionSource() }

    val background: Color = when (variant) {
        NpButtonVariant.Primary -> if (enabled) colors.accent else colors.line
        NpButtonVariant.Soft -> colors.surface2
        NpButtonVariant.Ghost, NpButtonVariant.Danger -> Color.Transparent
    }
    val contentColor: Color = when (variant) {
        NpButtonVariant.Primary -> if (enabled) colors.accentInk else colors.inkFaint
        NpButtonVariant.Soft -> colors.ink
        NpButtonVariant.Ghost -> colors.accent
        NpButtonVariant.Danger -> colors.protein
    }
    val borderColor: Color? = if (variant == NpButtonVariant.Soft) colors.line else null
    val showShadow = variant == NpButtonVariant.Primary && enabled

    val vPad: Dp = when (size) {
        NpButtonSize.Sm -> 8.dp
        NpButtonSize.Md -> 13.dp
        NpButtonSize.Lg -> 16.dp
    }
    val hPad: Dp = when (size) {
        NpButtonSize.Sm -> 14.dp
        NpButtonSize.Md -> 20.dp
        NpButtonSize.Lg -> 22.dp
    }
    val fontSize: TextUnit = when (size) {
        NpButtonSize.Sm -> 14.sp
        NpButtonSize.Md -> 15.5.sp
        NpButtonSize.Lg -> 16.5.sp
    }
    val iconSize: Dp = if (size == NpButtonSize.Sm) 17.dp else 19.dp

    Row(
        modifier = modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
            .pressScale(interaction)
            .then(if (showShadow) Modifier.softShadow(shape) else Modifier)
            .clip(shape)
            .background(background)
            .then(if (borderColor != null) Modifier.border(1.5.dp, borderColor, shape) else Modifier)
            .clickable(
                enabled = enabled,
                interactionSource = interaction,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = hPad, vertical = vPad),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            Icon(leadingIcon, contentDescription = null, tint = contentColor, modifier = Modifier.size(iconSize))
        }
        Text(
            text = text,
            style = NutriPathTheme.typography.button.copy(fontSize = fontSize),
            color = contentColor,
            maxLines = 1,
        )
    }
}
