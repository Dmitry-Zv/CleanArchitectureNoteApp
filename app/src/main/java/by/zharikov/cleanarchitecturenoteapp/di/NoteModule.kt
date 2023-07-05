package by.zharikov.cleanarchitecturenoteapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDao
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideMigration_1_2(): Migration =
        object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE note_table DROP COLUMN color INTEGER")
                database.execSQL("ALTER TABLE note_table ADD COLUMN color TEXT")
            }

        }

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context,
        MIGRATION_1_2: Migration
    ): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME)
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao =
        noteDatabase.noteDao()

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases =
        NoteUseCases(
            getNoteUseCase = GetNoteUseCase(repository),
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            updateNotesUseCase = UpdateNotesUseCase(repository)
        )


}