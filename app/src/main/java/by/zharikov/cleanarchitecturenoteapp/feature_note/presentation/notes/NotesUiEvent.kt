package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes

import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder

sealed class NotesUiEvent {
    data class Order(val order: NoteOrder) : NotesUiEvent()
    data class DeleteNote(val note: Note) : NotesUiEvent()
    object Restore: NotesUiEvent()
    object ToggleOrderSection : NotesUiEvent()
}

