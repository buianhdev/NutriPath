package vn.anhbt.nutripath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme
import vn.anhbt.nutripath.presentation.theme.softShadow

/**
 * Surface card. Mirrors `Card` in the design's `ui.jsx`: surface fill, hairline border, soft warm
 * shadow, density-aware corner radius and padding. Pass [onClick] to make it tappable.
 *
 * [padding] defaults to the active density's card padding when left [Dp.Unspecified].
 */
@Composable
fun NpCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    padding: Dp = Dp.Unspecified,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = NutriPathTheme.colors
    val shape = NutriPathTheme.shapes.card
    val resolvedPadding = if (padding == Dp.Unspecified) NutriPathTheme.dimens.cardPadding else padding

    Column(
        modifier = modifier
            .softShadow(shape)
            .clip(shape)
            .background(colors.surface)
            .border(1.dp, colors.line, shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(resolvedPadding),
        content = content,
    )
}
