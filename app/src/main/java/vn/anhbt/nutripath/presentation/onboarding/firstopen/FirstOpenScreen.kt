package vn.anhbt.nutripath.presentation.onboarding.firstopen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.anhbt.nutripath.presentation.common.NpButton
import vn.anhbt.nutripath.presentation.common.NpButtonSize
import vn.anhbt.nutripath.presentation.common.NpCard
import vn.anhbt.nutripath.presentation.common.NpIcons
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme

/**
 * Onboarding welcome screen. Mirrors the `step === 0` welcome view in the design's `onboarding.jsx`:
 * a diagonal accent band with the rotated logo + brand title, and a surface card listing three value
 * props with the primary "Build my plan" CTA.
 *
 * Stateless view only — [onContinue] advances to the planning flow. State/VM are wired separately.
 */
@Composable
fun FirstOpenScreen(
    onContinue: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val colors = NutriPathTheme.colors
    val typography = NutriPathTheme.typography

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                // Spec uses a 168deg diagonal split; approximated as a hard vertical split at 56%.
                Brush.verticalGradient(
                    0f to colors.accent,
                    0.56f to colors.accent,
                    0.56f to colors.bg,
                    1f to colors.bg,
                ),
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(24.dp),
        ) {
            // ── Brand block ───────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .rotate(-6f)
                        .shadow(20.dp, RoundedCornerShape(30.dp), clip = false)
                        .clip(RoundedCornerShape(30.dp))
                        .background(colors.accentInk),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(NpIcons.Leaf, contentDescription = null, tint = colors.accent, modifier = Modifier.size(52.dp))
                }
                Spacer(Modifier.height(28.dp))
                Text(
                    text = buildAnnotatedString {
                        append("NutriPath")
                        withStyle(SpanStyle(color = colors.accentInk.copy(alpha = 0.7f))) { append(" AI") }
                    },
                    style = typography.displayLarge,
                    color = colors.accentInk,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Your personal nutrition plan, built around your body and your goal.",
                    style = typography.body.copy(fontWeight = FontWeight.Medium, fontSize = 16.5.sp),
                    color = colors.accentInk.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 280.dp),
                )
            }

            // ── Value props + CTA ─────────────────────────────────
            NpCard(padding = 22.dp) {
                ValueProp(NpIcons.Scale, "Tell us about your body", "Weight, height, age & activity")
                HorizontalDivider(color = colors.lineSoft)
                ValueProp(NpIcons.Target, "Set a real goal", "Lose, gain or maintain at your pace")
                HorizontalDivider(color = colors.lineSoft)
                ValueProp(NpIcons.Flame, "Track every meal", "We do the calorie & macro math")
                Spacer(Modifier.height(16.dp))
                NpButton(
                    text = "Build my plan",
                    onClick = onContinue,
                    fullWidth = true,
                    size = NpButtonSize.Lg,
                    leadingIcon = NpIcons.Spark,
                )
            }
        }
    }
}

@Composable
private fun ValueProp(icon: ImageVector, title: String, subtitle: String) {
    val colors = NutriPathTheme.colors
    val typography = NutriPathTheme.typography
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(colors.surface2),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = colors.accent, modifier = Modifier.size(22.dp))
        }
        Column {
            Text(
                text = title,
                style = typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.5.sp),
                color = colors.ink,
            )
            Text(text = subtitle, style = typography.caption, color = colors.inkSoft)
        }
    }
}

@Preview(name = "FirstOpen", showBackground = true, heightDp = 820)
@Composable
private fun FirstOpenScreenPreview() {
    NutriPathTheme { FirstOpenScreen() }
}
