package by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository

import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeNoteRepository @Inject constructor() : NoteRepository {

    private val notesList = mutableListOf<Note>()

    override fun getAllNotes(): Flow<List<Note>> = flow {
        emit(notesList)
    }

    override suspend fun getNoteById(id: Int): Note? =
        notesList.find { it.id == id }

    override suspend fun insertNote(note: Note) {
        notesList.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notesList.remove(note)
    }

    override suspend fun updateNotes(notes: List<Note>) {
        notesList.addAll(notes)
    }
}