package vn.anhbt.nutripath.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vn.anhbt.nutripath.presentation.theme.NutriPathTheme

/** Labeled field wrapper. Mirrors `Field` in the design's `ui.jsx` (label + optional hint + control). */
@Composable
fun NpField(
    label: String,
    modifier: Modifier = Modifier,
    hint: String? = null,
    content: @Composable () -> Unit,
) {
    val colors = NutriPathTheme.colors
    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(label, style = NutriPathTheme.typography.label, color = colors.ink)
            if (hint != null) {
                Text(hint, style = NutriPathTheme.typography.caption, color = colors.inkFaint)
            }
        }
        Spacer(Modifier.height(9.dp))
        content()
    }
}

/** Single-line text input. Mirrors `TextInput` in the design's `ui.jsx` (focus accent border, suffix). */
@Composable
fun NpTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    suffix: String? = null,
    singleLine: Boolean = true,
) {
    val colors = NutriPathTheme.colors
    val shape = NutriPathTheme.shapes.input
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = NutriPathTheme.typography.body.copy(color = colors.ink),
        singleLine = singleLine,
        cursorBrush = SolidColor(colors.accent),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        interactionSource = interaction,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(colors.surface2)
                    .border(1.5.dp, if (focused) colors.accent else colors.line, shape)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(modifier = Modifier.weight(1f).padding(vertical = 13.dp)) {
                    if (value.isEmpty() && placeholder != null) {
                        Text(placeholder, style = NutriPathTheme.typography.body, color = colors.inkFaint)
                    }
                    innerTextField()
                }
                if (suffix != null) {
                    Text(suffix, style = NutriPathTheme.typography.bodySmall, color = colors.inkFaint)
                }
            }
        },
    )
}
