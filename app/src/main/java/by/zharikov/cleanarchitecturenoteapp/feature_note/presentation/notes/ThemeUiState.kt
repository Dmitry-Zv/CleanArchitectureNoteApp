package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes


data class ThemeUiState(
    var theme: Theme
)

sealed class Theme {
    object Light : Theme()
    object Dark : Theme()
}