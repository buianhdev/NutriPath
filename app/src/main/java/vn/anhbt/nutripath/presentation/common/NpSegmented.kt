package vn.anhbt.nutripath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import vn.anhbt.nutripath.presentation.theme.softShadow

data class SegmentOption<T>(
    val value: T,
    val label: String,
    val icon: ImageVector? = null,
)

/**
 * Segmented control. Mirrors `Segmented` in the design's `ui.jsx`: a surface2 track holding raised
 * white pills, optional per-segment icons, and a [columns] grid (options wrap into rows of [columns]).
 */
@Composable
fun <T> NpSegmented(
    options: List<SegmentOption<T>>,
    selected: T,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    columns: Int = options.size,
) {
    val colors = NutriPathTheme.colors
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(NutriPathTheme.shapes.segmented)
            .background(colors.surface2)
            .border(1.5.dp, colors.line, NutriPathTheme.shapes.segmented)
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        options.chunked(columns).forEach { rowOptions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                rowOptions.forEach { option ->
                    Segment(
                        label = option.label,
                        icon = option.icon,
                        active = option.value == selected,
                        onClick = { onSelect(option.value) },
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.Segment(
    label: String,
    icon: ImageVector?,
    active: Boolean,
    onClick: () -> Unit,
) {
    val colors = NutriPathTheme.colors
    val shape = RoundedCornerShape(12.dp)
    val interaction = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .weight(1f)
            .pressScale(interaction)
            .then(if (active) Modifier.softShadow(shape) else Modifier)
            .clip(shape)
            .background(if (active) colors.surface else Color.Transparent)
            .clickable(interactionSource = interaction, indication = null, onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 11.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        if (icon != null) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (active) colors.accent else colors.inkFaint,
                modifier = Modifier.size(20.dp),
            )
        }
        Text(
            text = label,
            style = NutriPathTheme.typography.bodySmall.copy(
                fontWeight = if (active) FontWeight.Bold else FontWeight.SemiBold,
            ),
            color = if (active) colors.ink else colors.inkSoft,
            maxLines = 1,
        )
    }
}
