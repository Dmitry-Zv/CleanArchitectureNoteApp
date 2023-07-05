package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.zharikov.cleanarchitecturenoteapp.R
import by.zharikov.cleanarchitecturenoteapp.core.utils.TestTags.ORDER_SECTION
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components.OrderSection
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components.ToggleThemeButton
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import by.zharikov.cleanarchitecturenoteapp.ui.theme.NoteTheme
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Log.d("INIT_NOTE", state.toString())
    Log.d("THEME_TAG", state.isDarkTheme.toString())
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = NoteTheme.colors.onBackground
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    tint = NoteTheme.colors.background
                )
            }
        },
        backgroundColor = NoteTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.your_note),
                    color = NoteTheme.colors.primary,
                    style = NoteTheme.typography.h4,
                    modifier = Modifier.weight(1f)
                )
                state.isDarkTheme?.let {
                    ToggleThemeButton(isDarkTheme = it) {
                        if (it) viewModel.onEvent(
                            event = NotesUiEvent.ToggleThemeButton(
                                false
                            )
                        )
                        else viewModel.onEvent(
                            event = NotesUiEvent.ToggleThemeButton(
                                true
                            )
                        )
                        Log.d("THEME_TAG", state.isDarkTheme.toString())
                    }
                }

                IconButton(onClick = { viewModel.onEvent(event = NotesUiEvent.ToggleOrderSection) }) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Sort",
                        tint = NoteTheme.colors.onBackground
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag(ORDER_SECTION),
                    noteOrder = state.noteOrder
                ) { noteOrder ->
                    viewModel.onEvent(event = NotesUiEvent.Order(order = noteOrder))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(contentPadding = it, modifier = Modifier.fillMaxSize()) {
                items(state.notes.size) { index ->
                    val note = state.notes[index]
                    NoteItem(note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("${Screen.AddEditNoteScreen.route}?noteId=${note.id}&noteColor=${note.color.second}")
                            },
                        onDeleteClick = {
                            viewModel.onEvent(event = NotesUiEvent.DeleteNote(note = note))
                            scope.launch {
                                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted", actionLabel = "Undo"
                                )
                                if (snackbarResult == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(event = NotesUiEvent.Restore)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}