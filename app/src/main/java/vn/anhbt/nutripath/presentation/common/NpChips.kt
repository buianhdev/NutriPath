package vn.anhbt.nutripath.presentation.common

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme

/**
 * Pill chip. Mirrors `Pill` in the design's `ui.jsx`. [color] / [background] default to the
 * secondary-text and surface2 tokens when left [Color.Unspecified].
 */
@Composable
fun NpPill(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    background: Color = Color.Unspecified,
    icon: ImageVector? = null,
) {
    val colors = NutriPathTheme.colors
    val foreground = if (color == Color.Unspecified) colors.inkSoft else color
    val surface = if (background == Color.Unspecified) colors.surface2 else background

    Row(
        modifier = modifier
            .clip(NutriPathTheme.shapes.pill)
            .background(surface)
            .padding(horizontal = 11.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = foreground, modifier = Modifier.size(14.dp))
        }
        Text(
            text = text,
            style = NutriPathTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
            color = foreground,
        )
    }
}

/**
 * Section heading with an optional trailing text action ("See all"). Mirrors `SectionLabel` in the
 * design's `ui.jsx`.
 */
@Composable
fun NpSectionLabel(
    title: String,
    modifier: Modifier = Modifier,
    action: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    val colors = NutriPathTheme.colors
    val interaction = remember { MutableInteractionSource() }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(title, style = NutriPathTheme.typography.sectionHeading, color = colors.ink)
        if (action != null && onActionClick != null) {
            Text(
                text = action,
                style = NutriPathTheme.typography.label,
                color = colors.accent,
                modifier = Modifier.clickable(
                    interactionSource = interaction,
                    indication = null,
                    onClick = onActionClick,
                ),
            )
        }
    }
}
