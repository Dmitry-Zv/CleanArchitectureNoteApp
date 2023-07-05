package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import by.zharikov.cleanarchitecturenoteapp.R
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.Theme
import by.zharikov.cleanarchitecturenoteapp.ui.theme.NoteTheme

@Composable
fun ToggleThemeButton(
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit
) {

    IconButton(onClick = toggleTheme) {
        Icon(
            painter = if (isDarkTheme) painterResource(id = R.drawable.ic_light_mode)
            else painterResource(id = R.drawable.ic_dark_mode),
            contentDescription = "Toggle dark / light theme",
            tint = NoteTheme.colors.onBackground
        )
    }
}