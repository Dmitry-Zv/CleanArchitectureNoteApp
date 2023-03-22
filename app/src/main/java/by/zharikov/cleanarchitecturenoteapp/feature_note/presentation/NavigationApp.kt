package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.NoteScreen
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.util.Screen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.NoteScreen.route) {
        composable(Screen.NoteScreen.route) {
            NoteScreen(navController = navController)
        }
        composable("${Screen.AddEditNoteScreen.route}?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId",
                    builder = {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                ),
                navArgument(
                    name = "noteColor",
                    builder = {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            )
        ) { navBackStackEntry ->
            val color = navBackStackEntry.arguments?.getInt("noteColor") ?: -1
            AddEditNoteScreen(navController = navController, noteColor = color)

        }
    }
}