package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.NotesViewModel
import by.zharikov.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import by.zharikov.cleanarchitecturenoteapp.ui.theme.NoteTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: NotesViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            Log.d("THEME_STATE", state.toString())
            state.isDarkTheme?.let {
                CleanArchitectureNoteAppTheme(isDarkTheme = it) {
                    // A surface container using the 'background' color from the theme
                    Surface {

                        val systemUiController = rememberSystemUiController()
                        val primaryBackGround = NoteTheme.colors.primaryVariant
                        SideEffect {
                            systemUiController.setSystemBarsColor(
                                color = primaryBackGround,
                                darkIcons = false
                            )
                        }
                        NavigationApp(viewModel = viewModel)
                    }
                }
            }

        }
    }
}

