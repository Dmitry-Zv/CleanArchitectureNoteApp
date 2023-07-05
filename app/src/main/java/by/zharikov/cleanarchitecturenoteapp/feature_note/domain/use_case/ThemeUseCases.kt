package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case

import javax.inject.Inject

data class ThemeUseCases @Inject constructor(
    val saveThemeInSharedPref: SaveThemeInSharedPref,
    val getThemeFromSharedPref: GetThemeFromSharedPref
)