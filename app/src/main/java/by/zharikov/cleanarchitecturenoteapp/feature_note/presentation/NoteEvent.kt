package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation

interface NoteEvent<in E> {

    fun onEvent(event: E)
}