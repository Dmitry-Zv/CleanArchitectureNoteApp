package by.zharikov.cleanarchitecturenoteapp.ui.theme

import android.graphics.CornerPathEffect
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp


data class NoteShape(
    val small: CornerBasedShape,
    val medium: CornerBasedShape,
    val large: CornerBasedShape
)

val shape = NoteShape(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)


val LocalNoteShape = staticCompositionLocalOf<NoteShape> {
    error("No shape provided")
}