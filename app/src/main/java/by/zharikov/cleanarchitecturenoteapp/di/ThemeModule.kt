package by.zharikov.cleanarchitecturenoteapp.di

import android.content.Context
import android.content.SharedPreferences
import by.zharikov.cleanarchitecturenoteapp.core.utils.SharedTags.THEME_PREF
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.SharedRepository
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.GetThemeFromSharedPref
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.SaveThemeInSharedPref
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case.ThemeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ThemeModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideThemeUseCases(repository: SharedRepository): ThemeUseCases =
        ThemeUseCases(
            saveThemeInSharedPref = SaveThemeInSharedPref(repository),
            getThemeFromSharedPref = GetThemeFromSharedPref(repository)
        )
}

