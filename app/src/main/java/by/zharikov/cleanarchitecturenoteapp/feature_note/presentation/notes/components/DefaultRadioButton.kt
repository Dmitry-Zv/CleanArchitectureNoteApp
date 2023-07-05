package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import by.zharikov.cleanarchitecturenoteapp.ui.theme.NoteTheme

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
                selectedColor = NoteTheme.colors.primary,
                unselectedColor = NoteTheme.colors.primaryVariant
            ),
            modifier = Modifier.semantics {
                contentDescription = text
            }
        )
        Text(text = text, style = NoteTheme.typography.body1, color = NoteTheme.colors.onSurface)
    }
}