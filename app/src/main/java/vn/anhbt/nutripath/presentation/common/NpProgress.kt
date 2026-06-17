package vn.anhbt.nutripath.presentation.common

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import vn.anhbt.nutripath.presentation.theme.DisplayFontFamily
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme
import kotlin.math.roundToLong

private val RingEasing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)

/**
 * Concentric calorie ring. Mirrors `CalorieRing` in the design's `ui.jsx`: an outer track + progress
 * arc (accent, or [NutriPathColors.protein] + full when over target) plus a thin inner depth ring.
 * Center content (the remaining/over number) goes in [content].
 */
@Composable
fun CalorieRing(
    consumed: Double,
    target: Double,
    modifier: Modifier = Modifier,
    size: Dp = 196.dp,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val colors = NutriPathTheme.colors
    val over = consumed > target
    val pct = if (target > 0) (consumed / target).coerceAtMost(1.0).toFloat() else 0f
    val progressColor = if (over) colors.protein else colors.accent
    val sweep by animateFloatAsState(
        targetValue = (if (over) 1f else pct) * 360f,
        animationSpec = tween(900, easing = RingEasing),
        label = "calorieRing",
    )
    val track = colors.ringTrack

    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val dim = this.size.minDimension
            val stroke = dim * 0.072f
            val inset = stroke / 2f + 2.dp.toPx()
            val arcSize = Size(dim - inset * 2, dim - inset * 2)
            val topLeft = Offset(inset, inset)

            drawArc(track, -90f, 360f, false, topLeft, arcSize, style = Stroke(stroke))
            drawArc(progressColor, -90f, sweep, false, topLeft, arcSize, style = Stroke(stroke, cap = StrokeCap.Round))

            val innerInset = inset + stroke + 6.dp.toPx()
            val innerSize = Size(dim - innerInset * 2, dim - innerInset * 2)
            drawArc(
                track.copy(alpha = 0.6f), -90f, 360f, false,
                Offset(innerInset, innerInset), innerSize, style = Stroke(3.dp.toPx()),
            )
        }
        content()
    }
}

/** Small single macro ring. Mirrors `MacroRing` in the design's `ui.jsx`. */
@Composable
fun MacroRing(
    value: Double,
    target: Double,
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 58.dp,
) {
    val track = NutriPathTheme.colors.ringTrack
    val pct = if (target > 0) (value / target).coerceAtMost(1.0).toFloat() else 0f
    val sweep by animateFloatAsState(
        targetValue = pct * 360f,
        animationSpec = tween(800, easing = RingEasing),
        label = "macroRing",
    )
    Canvas(modifier.size(size)) {
        val stroke = 6.dp.toPx()
        val inset = stroke / 2f
        val arcSize = Size(this.size.width - inset * 2, this.size.height - inset * 2)
        val topLeft = Offset(inset, inset)
        drawArc(track, -90f, 360f, false, topLeft, arcSize, style = Stroke(stroke))
        drawArc(color, -90f, sweep, false, topLeft, arcSize, style = Stroke(stroke, cap = StrokeCap.Round))
    }
}

/**
 * Macro progress bar with label and value / target. Mirrors `MacroBar` in the design's `ui.jsx`.
 * The numeric value uses the display (number) font.
 */
@Composable
fun MacroBar(
    label: String,
    value: Double,
    target: Double,
    color: Color,
    modifier: Modifier = Modifier,
    unit: String = "g",
) {
    val colors = NutriPathTheme.colors
    val pct = if (target > 0) (value / target).coerceIn(0.0, 1.0).toFloat() else 0f
    val fraction by animateFloatAsState(
        targetValue = pct,
        animationSpec = tween(800, easing = RingEasing),
        label = "macroBar",
    )
    val numStyle = NutriPathTheme.typography.caption.copy(fontFamily = DisplayFontFamily)

    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(label, style = NutriPathTheme.typography.caption, color = colors.inkSoft)
            Row(verticalAlignment = Alignment.Bottom) {
                Text("${value.roundToLong()}", style = numStyle, color = colors.ink)
                Text("/${target.roundToLong()}$unit", style = numStyle, color = colors.inkFaint)
            }
        }
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(colors.ringTrack),
        ) {
            Box(
                Modifier
                    .fillMaxWidth(fraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(color),
            )
        }
    }
}
