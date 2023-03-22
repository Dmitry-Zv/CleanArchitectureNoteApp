package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.GetNoteUseCase
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.NoteEvent
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(), NoteEvent<AddEditNoteUiEvent> {

    private val _noteTitle = MutableStateFlow(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )
    val noteTitle = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow(
        NoteTextFieldState(
            hint = "Enter content..."
        )
    )
    val noteContent = _noteContent.asStateFlow()

    private val _noteColor = MutableStateFlow(Note.notesColor.random().toArgb())
    val noteColor = _noteColor.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val msg: String) : UiEvent()
        object SaveNote : UiEvent()
    }

    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCase.getNoteUseCase(noteId)?.also { note ->
                        currentId = note.id
                        _noteTitle.update {
                            it.copy(noteText = note.title, isHintVisible = false)
                        }
                        _noteContent.update {
                            it.copy(noteText = note.content, isHintVisible = false)
                        }
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    override fun onEvent(event: AddEditNoteUiEvent) {
        when (event) {
            is AddEditNoteUiEvent.ChangeColor -> changeColor(color = event.color)
            is AddEditNoteUiEvent.ChangeContentFocus -> changeContentFocus(isFocus = event.focusState.isFocused)
            is AddEditNoteUiEvent.ChangeTitleFocus -> changeTitleFocus(isFocus = event.focusState.isFocused)
            is AddEditNoteUiEvent.EnteredContent -> enteredContent(content = event.value)
            is AddEditNoteUiEvent.EnteredTitle -> enteredTitle(title = event.value)
            AddEditNoteUiEvent.SaveNote -> saveNote()
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                val note = Note(
                    title = noteTitle.value.noteText,
                    content = noteContent.value.noteText,
                    timestamp = System.currentTimeMillis(),
                    color = noteColor.value,
                    id = currentId
                )
                noteUseCase.addNoteUseCase(note = note)
                _eventFlow.emit(UiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(UiEvent.ShowSnackbar(msg = e.message ?: "Couldn't save note"))
            }
        }
    }

    private fun changeTitleFocus(isFocus: Boolean) {
        _noteTitle.update {
            it.copy(isHintVisible = !isFocus && it.noteText.isBlank())
        }
    }

    private fun enteredTitle(title: String) {
        _noteTitle.update {
            it.copy(
                noteText = title
            )
        }
    }

    private fun enteredContent(content: String) {
        _noteContent.update {
            it.copy(
                noteText = content
            )
        }
    }

    private fun changeContentFocus(isFocus: Boolean) {
        _noteContent.update {
            it.copy(
                isHintVisible = !isFocus && it.noteText.isBlank()
            )
        }
    }

    private fun changeColor(color: Int) {
        _noteColor.value = color
    }
}