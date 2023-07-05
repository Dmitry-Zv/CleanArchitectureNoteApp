package by.zharikov.cleanarchitecturenoteapp.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import by.zharikov.cleanarchitecturenoteapp.core.utils.SharedTags
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDao
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository.SharedRepositoryImpl
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.SharedRepository
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestModule {

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
        Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao =
        database.noteDao()

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases =
        NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository),
            updateNotesUseCase = UpdateNotesUseCase(repository)
        )


    @Provides
//    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext<Context?>().applicationContext)

    @Provides
//    @Singleton
    fun provideThemeUseCases(repository: SharedRepository): ThemeUseCases =
        ThemeUseCases(
            saveThemeInSharedPref = SaveThemeInSharedPref(repository),
            getThemeFromSharedPref = GetThemeFromSharedPref(repository)
        )

}

@Module
@InstallIn(SingletonComponent::class)
interface FakeRepModule {
    @Binds
    fun bindNoteRepository(fakeNoteRepository: NoteRepositoryImpl): NoteRepository

    @Binds
    fun bindSharedRepositoryImpl_toSharedRepository(sharedRepositoryImpl: SharedRepositoryImpl): SharedRepository
}