package vn.anhbt.nutripath.presentation.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

/**
 * NutriPath icon set — the stroke icons from the design's `core.jsx`, recreated as [ImageVector]s so
 * they match the prototype exactly (lightweight round-capped strokes) without pulling in
 * material-icons-extended.
 *
 * All icons are 24×24, drawn in black, and meant to be rendered via `Icon(..., tint = <color>)` which
 * recolors them to the desired token. SVG `<circle>` / `<rect>` elements are converted to path data
 * by [circlePath] / [roundRectPath].
 */
object NpIcons {
    val Home = strokeIcon("M4 11.5 12 4l8 7.5", "M6 10v9.5h12V10")
    val Book = strokeIcon(
        "M5 4.5h11a2 2 0 0 1 2 2V20H7a2 2 0 0 1-2-2V4.5Z",
        "M5 4.5A1.5 1.5 0 0 1 6.5 6V20",
        "M9.5 9h5M9.5 12.5h5",
    )
    val User = strokeIcon(circlePath(12.0, 8.5, 3.5), "M5.5 19.5a6.5 6.5 0 0 1 13 0")
    val Plus = strokeIcon("M12 5v14M5 12h14")
    val Search = strokeIcon(circlePath(11.0, 11.0, 6.0), "m20 20-3.5-3.5")
    val Flame = strokeIcon("M12 3c.5 3-2.5 4.5-2.5 7.5a2.5 2.5 0 0 0 5 0c0-1 .5-1.5.5-1.5.8 1 2 2.6 2 4.8A5.5 5.5 0 0 1 6.5 14C6.5 8.5 12 8 12 3Z")
    val Trash = strokeIcon(
        "M5 7h14M9.5 7V5.5a1.5 1.5 0 0 1 1.5-1.5h2a1.5 1.5 0 0 1 1.5 1.5V7",
        "M7 7l.8 11a2 2 0 0 0 2 1.8h4.4a2 2 0 0 0 2-1.8L17 7",
    )
    val Close = strokeIcon("M6 6l12 12M18 6 6 18")
    val ChevronRight = strokeIcon("m9 5 7 7-7 7")
    val ChevronLeft = strokeIcon("m15 5-7 7 7 7")
    val ChevronDown = strokeIcon("m5 9 7 7 7-7")
    val Check = strokeIcon("m4 12.5 5 5L20 6.5")
    val Edit = strokeIcon(
        "M4 20h4L18.5 9.5a2 2 0 0 0 0-3l-1-1a2 2 0 0 0-3 0L4 16v4Z",
        "M13.5 7.5 17 11",
    )
    val Scale = strokeIcon(
        roundRectPath(4.0, 6.0, 16.0, 14.0, 3.0),
        "M9 6V5a3 3 0 0 1 6 0v1",
        "M12 10v3.5M12 13.5l2-2M12 13.5l-2-2",
    )
    val Bolt = strokeIcon("M13 3 5 13h6l-1 8 8-10h-6l1-8Z")
    val Target = mixedIcon(
        strokes = listOf(circlePath(12.0, 12.0, 8.0), circlePath(12.0, 12.0, 4.0)),
        fills = listOf(circlePath(12.0, 12.0, 1.4)),
    )
    val Back = strokeIcon("M11 5 4 12l7 7M4 12h16")
    val Spark = mixedIcon(
        strokes = listOf("M12 3v4M12 17v4M3 12h4M17 12h4M6.5 6.5l2.5 2.5M15 15l2.5 2.5M17.5 6.5 15 9M9 15l-2.5 2.5"),
        fills = listOf(circlePath(12.0, 12.0, 2.4)),
    )
    val Leaf = strokeIcon("M5 19C5 11 11 5 19 5c0 8-6 14-14 14Z", "M9 15c2-3 5-5 8-6")
    val Clock = strokeIcon(circlePath(12.0, 12.0, 8.0), "M12 8v4.5l3 1.8")
    val Male = strokeIcon(circlePath(10.0, 14.0, 5.0), "M14 10l6-6M15 4h5v5")
    val Female = strokeIcon(circlePath(12.0, 9.0, 5.0), "M12 14v7M9 18h6")
    val Minus = strokeIcon("M5 12h14")
    val Calendar = strokeIcon(
        roundRectPath(4.0, 5.5, 16.0, 15.0, 3.0),
        "M4 10h16M8.5 3.5v4M15.5 3.5v4",
    )
    val Dumbbell = strokeIcon("M3 9v6M6 7v10M18 7v10M21 9v6M6 12h12")
}

// ── builders ──────────────────────────────────────────────────────────────────

private const val STROKE_WIDTH = 2f

private fun strokeIcon(vararg pathData: String): ImageVector =
    buildIcon(strokes = pathData.toList(), fills = emptyList())

private fun mixedIcon(strokes: List<String>, fills: List<String>): ImageVector =
    buildIcon(strokes = strokes, fills = fills)

private fun buildIcon(strokes: List<String>, fills: List<String>): ImageVector =
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        strokes.forEach { d ->
            addPath(
                pathData = PathParser().parsePathString(d).toNodes(),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = STROKE_WIDTH,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            )
        }
        fills.forEach { d ->
            addPath(
                pathData = PathParser().parsePathString(d).toNodes(),
                fill = SolidColor(Color.Black),
            )
        }
    }.build()

/** A full circle expressed as two arcs, so it can go through [PathParser]. */
private fun circlePath(cx: Double, cy: Double, r: Double): String =
    "M ${cx - r} $cy a $r $r 0 1 0 ${r * 2} 0 a $r $r 0 1 0 ${-r * 2} 0 Z"

/** A rounded rectangle as path data. */
private fun roundRectPath(x: Double, y: Double, w: Double, h: Double, r: Double): String {
    val midW = w - 2 * r
    val midH = h - 2 * r
    return "M ${x + r} $y " +
        "h $midW a $r $r 0 0 1 $r $r " +
        "v $midH a $r $r 0 0 1 ${-r} $r " +
        "h ${-midW} a $r $r 0 0 1 ${-r} ${-r} " +
        "v ${-midH} a $r $r 0 0 1 $r ${-r} Z"
}
