package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@androidx.compose.runtime.Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.primaryVariant
            )
        )
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}