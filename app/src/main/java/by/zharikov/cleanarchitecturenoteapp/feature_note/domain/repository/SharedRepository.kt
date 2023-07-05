package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository

import android.content.SharedPreferences

interface SharedRepository {

    suspend fun savePref(isDarkTheme: Boolean)

    suspend fun getPref(): Boolean
}