package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case

import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.SharedRepository
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.Theme
import javax.inject.Inject

class SaveThemeInSharedPref @Inject constructor(private val repository: SharedRepository) {

    suspend operator fun invoke(isDarkTheme: Boolean) {
        repository.savePref(isDarkTheme)
    }
}