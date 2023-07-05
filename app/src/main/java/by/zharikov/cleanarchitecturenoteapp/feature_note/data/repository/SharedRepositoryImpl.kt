package by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository

import android.content.SharedPreferences
import by.zharikov.cleanarchitecturenoteapp.core.utils.SharedTags.THEME_MODE
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.SharedRepository
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SharedRepository {

    override suspend fun savePref(isDarkTheme: Boolean) {
        sharedPreferences.edit().putBoolean(THEME_MODE, isDarkTheme).apply()
    }

    override suspend fun getPref(): Boolean =
        sharedPreferences.getBoolean(THEME_MODE, false)


}