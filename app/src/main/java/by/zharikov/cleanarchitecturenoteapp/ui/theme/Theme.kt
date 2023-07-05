package by.zharikov.cleanarchitecturenoteapp.ui.theme

import android.graphics.CornerPathEffect
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.Theme


object NoteTheme {
    val colors: NoteColors
        @Composable
        get() = LocalNoteColors.current

    val typography: NoteTypography
        @Composable
        get() = LocalNoteTypography.current
    val shape: NoteShape
        @Composable
        get() = LocalNoteShape.current

    /**
     * Retrieves the current [Shapes] at the call site's position in the hierarchy.
     */

}

@Composable
fun CleanArchitectureNoteAppTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {

//    MaterialTheme(
//        colors = if (theme == Theme.Dark) DarkColorPalette else LightColorPalette,
//        typography = Typography,
//        shapes = Shapes,
//        content = content
//    )
    NoteTheme(
        isDarkTheme = isDarkTheme,
        content = content
    )
}


@Composable
fun NoteTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) darkNoteColorPalette
    else lightNoteColorPalette

    val typography = typography

    val shape = shape

    CompositionLocalProvider(
        LocalNoteColors provides colors,
        LocalNoteTypography provides typography,
        LocalNoteShape provides shape,
        content = content
    )

}




