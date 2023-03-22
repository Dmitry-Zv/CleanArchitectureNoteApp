package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.zharikov.cleanarchitecturenoteapp.R
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.components.OrderSection
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.your_note),
                    style = MaterialTheme.typography.h4
                )
                IconButton(onClick = { viewModel.onEvent(event = NotesUiEvent.ToggleOrderSection) }) {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Sort")
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
                        .padding(vertical = 16.dp),
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
                                navController.navigate("${Screen.AddEditNoteScreen.route}?noteId=${note.id}&noteColor=${note.color}")
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