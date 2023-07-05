package by.zharikov.cleanarchitecturenoteapp.di

import by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository.SharedRepositoryImpl
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.SharedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepModule {

    @Binds
    fun bindNoteRepositoryImpl_toNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository

    @Binds
    fun bindSharedRepositoryImpl_toSharedRepository(sharedRepositoryImpl: SharedRepositoryImpl): SharedRepository

}