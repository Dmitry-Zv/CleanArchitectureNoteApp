package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import by.zharikov.cleanarchitecturenoteapp.core.utils.TestTags
import by.zharikov.cleanarchitecturenoteapp.di.AppModule
import by.zharikov.cleanarchitecturenoteapp.di.RepModule
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes.NoteScreen
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import by.zharikov.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class, RepModule::class)
class NotesEndToEndTest {


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CleanArchitectureNoteAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.NoteScreen.route) {
                    composable(Screen.NoteScreen.route) {
                        NoteScreen(navController = navController)
                    }

                    composable(
                        "${Screen.AddEditNoteScreen.route}?noteId={noteId}&noteColor={noteColor}",
                        listOf(
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
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        composeRule.onNodeWithContentDescription("Add Note").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("text title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIElD).performTextInput("text content")
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("text title").assertIsDisplayed()
        composeRule.onNodeWithText("text title").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("text title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIElD).assertTextEquals("text content")
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput(" 2")
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("text title 2").assertIsDisplayed()

    }


    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add Note").performClick()

            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("text title $i")
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIElD).performTextInput("text content $i")
            composeRule.onNodeWithContentDescription("Save note").performClick()

            composeRule.onNodeWithText("text title $i").assertIsDisplayed()
        }
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("text title 3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("text title 2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("text title 1")
    }


}