package by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import by.zharikov.cleanarchitecturenoteapp.core.utils.ColorMapping
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note

@Database(entities = [Note::class], version = 2)
@TypeConverters(ColorMapping::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao


    companion object {
        const val DATABASE_NAME = "note_database"
    }

}