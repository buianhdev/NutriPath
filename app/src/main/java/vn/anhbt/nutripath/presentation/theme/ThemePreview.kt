package vn.anhbt.nutripath.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp

/**
 * Visual reference for the design system tokens. Not part of the product UI — exists so the palette,
 * type scale, and surfaces can be reviewed at a glance. NOTE: downloadable Google Fonts won't render
 * in Android Studio's static preview (the provider needs Play services); run on a device/emulator to
 * see the real Bricolage Grotesque / Plus Jakarta Sans.
 */
@Composable
private fun TokenGallery() {
    val c = NutriPathTheme.colors
    val type = NutriPathTheme.typography
    Column(
        modifier = Modifier
            .background(c.bg)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        // ── Type scale ────────────────────────────────────────────
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TypeRow("Display", type.displayLarge, c.ink)
            TypeRow("Screen title", type.screenTitle, c.ink)
            TypeRow("Sheet title", type.sheetTitle, c.ink)
            TypeRow("Section heading", type.sectionHeading, c.ink)
            TypeRow("1,840", type.statHero, c.accent)
            TypeRow("Body / UI text", type.body, c.ink)
            TypeRow("Field label", type.label, c.ink)
            TypeRow("Caption text", type.caption, c.inkSoft)
            TypeRow("STEP 1 OF 4", type.kicker, c.accent)
        }

        // ── Color swatches ────────────────────────────────────────
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Swatch("accent", c.accent)
            Swatch("protein", c.protein)
            Swatch("carb", c.carb)
            Swatch("fat", c.fat)
            Swatch("good", c.good)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Swatch("bg", c.bg)
            Swatch("surface", c.surface)
            Swatch("surface2", c.surface2)
            Swatch("line", c.line)
            Swatch("ringTrack", c.ringTrack)
        }

        // ── Surfaces ──────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .softShadow(NutriPathTheme.shapes.card)
                .background(c.surface, NutriPathTheme.shapes.card)
                .border(1.dp, c.line, NutriPathTheme.shapes.card)
                .padding(NutriPathTheme.dimens.cardPadding),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text("Card surface", style = type.sectionHeading, color = c.ink)
            Text("Soft warm shadow, density-aware radius & padding.", style = type.caption, color = c.inkSoft)
        }
    }
}

@Composable
private fun TypeRow(text: String, style: TextStyle, color: Color) {
    Text(text = text, style = style, color = color)
}

@Composable
private fun Swatch(name: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            Modifier
                .size(48.dp)
                .background(color, RoundedCornerShape(12.dp))
                .border(1.dp, NutriPathTheme.colors.line, RoundedCornerShape(12.dp)),
        )
        Spacer(Modifier.height(4.dp))
        Text(name, style = NutriPathTheme.typography.micro, color = NutriPathTheme.colors.inkSoft)
    }
}

@Preview(name = "Tokens — Light", showBackground = true, heightDp = 900)
@Composable
private fun TokenGalleryLightPreview() {
    NutriPathTheme(darkTheme = false) { TokenGallery() }
}

@Preview(name = "Tokens — Dark", showBackground = true, heightDp = 900)
@Composable
private fun TokenGalleryDarkPreview() {
    NutriPathTheme(darkTheme = true) { TokenGallery() }
}

@Preview(name = "Tokens — Berry accent", showBackground = true, heightDp = 900)
@Composable
private fun TokenGalleryBerryPreview() {
    NutriPathTheme(darkTheme = false, accent = AccentColor.Berry) { TokenGallery() }
}
