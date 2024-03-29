package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.zharikov.cleanarchitecturenoteapp.core.utils.ColorTags
import by.zharikov.cleanarchitecturenoteapp.core.utils.TestTags
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.components.TransparentTextField
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import by.zharikov.cleanarchitecturenoteapp.ui.theme.NoteTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState by viewModel.noteTitle.collectAsState()
    val contentState by viewModel.noteContent.collectAsState()
    val colorState by viewModel.noteColor.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    val listOfMapOfNoteColors = mapOf(
        ColorTags.RED_ORANGE to NoteTheme.colors.redOrange,
        ColorTags.LIGHT_GREEN to NoteTheme.colors.lightGreen,
        ColorTags.BABY_BLUE to NoteTheme.colors.babyBlue,
        ColorTags.RED_PINK to NoteTheme.colors.redPink,
        ColorTags.VIOLET to NoteTheme.colors.violet
    )

    Log.d("EVENT", colorState.toString())

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else colorState.second)
        )
    }
    Log.d("EVENT", noteBackgroundAnimatable.value.toArgb().toString())
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditNoteViewModel.UiEvent.SaveNote -> navController.navigateUp()
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg
                    )
                }
            }
        }
    }




    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(event = AddEditNoteUiEvent.SaveNote) },
                backgroundColor = NoteTheme.colors.onBackground
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Save note",
                    tint = NoteTheme.colors.background
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(it)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                listOfMapOfNoteColors.forEach { color ->
                    val colorInt = color.value.toArgb()
                    val colorPair = Pair(color.key, colorInt)
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color.value)
                            .border(
                                width = 3.dp,
                                color = if (colorInt == colorState.second) Color.Black
                                else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(event = AddEditNoteUiEvent.ChangeColor(colorPair = colorPair))
                            }
                    )


                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = titleState.noteText,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(event = AddEditNoteUiEvent.EnteredTitle(value = it))
                },
                onFocusChange = {
                    viewModel.onEvent(event = AddEditNoteUiEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                testTag = TestTags.TITLE_TEXT_FIELD,
                textStyle = NoteTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = contentState.noteText,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(event = AddEditNoteUiEvent.EnteredContent(value = it))
                },
                onFocusChange = {
                    viewModel.onEvent(event = AddEditNoteUiEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = NoteTheme.typography.body1,
                testTag = TestTags.CONTENT_TEXT_FIElD,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}