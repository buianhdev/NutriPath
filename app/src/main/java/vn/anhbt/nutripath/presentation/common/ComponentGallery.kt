package vn.anhbt.nutripath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme

/**
 * Visual reference for the shared primitives (not product UI). Lets the components be reviewed at a
 * glance. Reminder: downloadable Google Fonts and the bottom sheet don't render in the static
 * preview — run on a device/emulator for those.
 */
@Composable
private fun ComponentGallery() {
    val colors = NutriPathTheme.colors
    var goal by remember { mutableStateOf("maintain") }
    var weight by remember { mutableDoubleStateOf(70.0) }
    var age by remember { mutableDoubleStateOf(28.0) }
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(colors.bg)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        // Buttons
        NpSectionLabel("Buttons")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            NpButton("Primary", onClick = {})
            NpButton("Soft", onClick = {}, variant = NpButtonVariant.Soft)
            NpButton("Ghost", onClick = {}, variant = NpButtonVariant.Ghost)
        }
        NpButton("Build my plan", onClick = {}, fullWidth = true, leadingIcon = NpIcons.Spark)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            NpButton("Small", onClick = {}, size = NpButtonSize.Sm)
            NpButton("Disabled", onClick = {}, enabled = false)
            NpButton("Remove", onClick = {}, variant = NpButtonVariant.Danger, leadingIcon = NpIcons.Trash, size = NpButtonSize.Sm)
        }

        // Segmented
        NpSectionLabel("Segmented")
        NpSegmented(
            options = listOf(
                SegmentOption("lose", "Lose"),
                SegmentOption("maintain", "Maintain"),
                SegmentOption("gain", "Gain"),
            ),
            selected = goal,
            onSelect = { goal = it },
        )

        // Stepper
        NpSectionLabel("Stepper")
        NpCard {
            NpStepper(value = weight, onValueChange = { weight = it }, unit = "kg", step = 0.5, big = true)
            Spacer(Modifier.height(12.dp))
            NpStepper(value = age, onValueChange = { age = it }, unit = "yr")
        }

        // Field + input
        NpSectionLabel("Inputs")
        NpField(label = "Meal name", hint = "optional") {
            NpTextInput(value = name, onValueChange = { name = it }, placeholder = "e.g. Chicken salad")
        }
        NpTextInput(value = "", onValueChange = {}, placeholder = "Calories", keyboardType = KeyboardType.Number, suffix = "kcal")

        // Rings + bars
        NpSectionLabel("Calorie ring & macros")
        NpCard {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CalorieRing(consumed = 1320.0, target = 1840.0, size = 150.dp) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("520", style = NutriPathTheme.typography.statLarge, color = colors.ink)
                        Text("LEFT", style = NutriPathTheme.typography.kicker, color = colors.inkFaint)
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MacroRing(value = 90.0, target = 126.0, color = colors.protein)
                    MacroRing(value = 40.0, target = 63.0, color = colors.fat)
                }
            }
            Spacer(Modifier.height(14.dp))
            MacroBar("Protein", 90.0, 126.0, colors.protein)
            Spacer(Modifier.height(10.dp))
            MacroBar("Carbs", 150.0, 210.0, colors.carb)
            Spacer(Modifier.height(10.dp))
            MacroBar("Fat", 40.0, 63.0, colors.fat)
        }

        // Pills
        NpSectionLabel("Pills", action = "See all", onActionClick = {})
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NpPill("Active", color = colors.accent, background = colors.surface2)
            NpPill("On track", color = colors.good, background = colors.goodBg, icon = NpIcons.Check)
            NpPill("520 kcal left", icon = NpIcons.Flame)
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Preview(name = "Components — Light", showBackground = true, heightDp = 1600)
@Composable
private fun ComponentGalleryLightPreview() {
    NutriPathTheme(darkTheme = false) { ComponentGallery() }
}

@Preview(name = "Components — Dark", showBackground = true, heightDp = 1600)
@Composable
private fun ComponentGalleryDarkPreview() {
    NutriPathTheme(darkTheme = true) { ComponentGallery() }
}
