package by.zharikov.cleanarchitecturenoteapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val DarkGray = Color(0xFF202020)
val LightBlue = Color(0xFFE7E6E6)

val RedOrangeDark = Color(0xffffab91)
val RedPinkDark = Color(0xfff48fb1)
val BabyBlueDark = Color(0xff81deea)
val VioletDark = Color(0xffcf94da)
val LightGreenDark = Color(0xffe7ed9b)

val RedOrangeLight = Color(0xffff5622)
val RedPinkLight = Color(0xffe91e62)
val BabyBlueLight = Color(0xff0cbcd4)
val VioletLight = Color(0xff9e29b3)
val LightGreenLight = Color(0xffd0da37)

data class NoteColors(
    val primary: Color,
    val primaryVariant: Color,
    val onBackground: Color,
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val redOrange: Color,
    val redPink: Color,
    val babyBlue: Color,
    val violet: Color,
    val lightGreen: Color
)


val darkNoteColorPalette = NoteColors(
    primary = Color.White,
    primaryVariant = DarkGray,
    background = Color.Black,
    onBackground = Color.White,
    surface = DarkGray,
    onSurface = LightBlue,
    redOrange = RedOrangeDark,
    redPink = RedPinkDark,
    babyBlue = BabyBlueDark,
    violet = VioletDark,
    lightGreen = LightGreenDark
)

val lightNoteColorPalette = NoteColors(
    primary = Color.Black,
    primaryVariant = Color.LightGray,
    background = Color.White,
    onBackground = Color.Black,
    surface = LightBlue,
    onSurface = DarkGray,
    redOrange = RedOrangeLight,
    redPink = RedPinkLight,
    babyBlue = BabyBlueLight,
    violet = VioletLight,
    lightGreen = LightGreenLight
)

val LocalNoteColors = staticCompositionLocalOf<NoteColors> {
    error("No colors provided")
}
