package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.NoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel(), NoteEvent<NotesUiEvent> {
    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()
    private var recentlyDeleteNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        performNotes(order = NoteOrder.Date(orderType = OrderType.Descending))
    }

    override fun onEvent(event: NotesUiEvent) {
        when (event) {
            is NotesUiEvent.Order -> {
                if (state.value.noteOrder::class == event.order::class
                    && state.value.noteOrder.orderType == event.order.orderType
                ) return
                performNotes(order = event.order)

            }
            is NotesUiEvent.DeleteNote -> {
                deleteNote(note = event.note)
            }
            is NotesUiEvent.Restore -> {
                performRestoreNotes()
            }
            is NotesUiEvent.ToggleOrderSection -> {
                performToggleOrderSection()
            }
        }
    }

    private fun performRestoreNotes() {
        viewModelScope.launch {
            recentlyDeleteNote?.let { note ->
                noteUseCases.addNoteUseCase(note = note)
            }
            recentlyDeleteNote = null
        }
    }

    private fun performToggleOrderSection() {
        _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNoteUseCase(note = note)
            recentlyDeleteNote = note
        }
    }

    private fun performNotes(order: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteOrder = order)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = order
                )
            }.launchIn(viewModelScope)


    }

}