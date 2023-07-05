package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case

import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.repository.SharedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetThemeFromSharedPref @Inject constructor(private val repository: SharedRepository) {

    suspend operator fun invoke(): Boolean = repository.getPref()
}