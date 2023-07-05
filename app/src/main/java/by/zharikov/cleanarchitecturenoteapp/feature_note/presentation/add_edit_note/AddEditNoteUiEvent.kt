package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color

sealed class AddEditNoteUiEvent {
    data class EnteredTitle(val value: String) : AddEditNoteUiEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteUiEvent()
    data class EnteredContent(val value: String) : AddEditNoteUiEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteUiEvent()
    data class ChangeColor(val colorPair: Pair<String, Int>) : AddEditNoteUiEvent()
    object SaveNote : AddEditNoteUiEvent()
}
