package by.zharikov.cleanarchitecturenoteapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDao
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
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
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
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
            getNoteUseCase = GetNoteUseCase(repository)
        )

}

@Module
@InstallIn(SingletonComponent::class)
interface FakeRepModule {
    @Binds
    @Singleton
    fun bindNoteRepository(fakeNoteRepository: NoteRepositoryImpl): NoteRepository
}