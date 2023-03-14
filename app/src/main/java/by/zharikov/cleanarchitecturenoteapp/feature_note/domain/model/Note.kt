package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.zharikov.cleanarchitecturenoteapp.ui.theme.*

@Entity(tableName = "note_table")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
    companion object {
        val notesColor = listOf(
            RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}