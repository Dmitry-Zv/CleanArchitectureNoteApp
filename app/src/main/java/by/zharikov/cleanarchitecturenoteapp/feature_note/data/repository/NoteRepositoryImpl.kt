package by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository

import by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDao
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes()


    override suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id = id)


    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note = note)
    }

    override suspend fun updateNotes(notes: List<Note>) {
        noteDao.updateNotes(notes = notes)
    }
}