package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case

import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? =
        repository.getNoteById(id = id)

}