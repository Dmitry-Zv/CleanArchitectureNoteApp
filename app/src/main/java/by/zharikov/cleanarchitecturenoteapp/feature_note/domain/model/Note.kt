package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.zharikov.cleanarchitecturenoteapp.core.utils.ColorTags
import by.zharikov.cleanarchitecturenoteapp.ui.theme.*
import com.google.gson.annotations.Expose

@Entity(tableName = "note_table")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    @Expose
    @ColumnInfo("color")
    var color: Pair<String, Int>,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
    companion object {
        val notesColorDark = mapOf(
            ColorTags.RED_ORANGE to RedOrangeDark.toArgb(),
            ColorTags.LIGHT_GREEN to LightGreenDark.toArgb(),
            ColorTags.VIOLET to VioletDark.toArgb(),
            ColorTags.BABY_BLUE to BabyBlueDark.toArgb(),
            ColorTags.RED_PINK to RedPinkDark.toArgb()
        )

        val notesColorLight = mapOf(
            ColorTags.RED_ORANGE to RedOrangeLight.toArgb(),
            ColorTags.LIGHT_GREEN to LightGreenLight.toArgb(),
            ColorTags.VIOLET to VioletLight.toArgb(),
            ColorTags.BABY_BLUE to BabyBlueLight.toArgb(),
            ColorTags.RED_PINK to RedPinkLight.toArgb()
        )

    }
}


class InvalidNoteException(msgError: String) : Exception(msgError)