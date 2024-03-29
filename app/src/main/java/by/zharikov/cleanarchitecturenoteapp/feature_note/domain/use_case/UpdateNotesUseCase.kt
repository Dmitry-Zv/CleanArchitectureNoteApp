package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case

import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(notes: List<Note>) {
        noteRepository.updateNotes(notes = notes)
    }
}