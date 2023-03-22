package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

data class NoteTextFieldState(
    val noteText: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
