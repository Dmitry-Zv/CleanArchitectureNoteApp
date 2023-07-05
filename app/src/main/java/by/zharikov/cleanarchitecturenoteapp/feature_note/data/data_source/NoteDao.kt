package by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.*
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Update
    suspend fun updateNotes(notes: List<Note>)


}