package by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository

import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeNoteRepository @Inject constructor() : NoteRepository {

    private val notes = mutableListOf<Note>()

    override fun getAllNotes(): Flow<List<Note>> = flow {
        emit(notes)
    }

    override suspend fun getNoteById(id: Int): Note? =
        notes.find { it.id == id }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}