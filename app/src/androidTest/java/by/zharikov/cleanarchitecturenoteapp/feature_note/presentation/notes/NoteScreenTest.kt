package by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.zharikov.cleanarchitecturenoteapp.core.utils.TestTags
import by.zharikov.cleanarchitecturenoteapp.di.AppModule
import by.zharikov.cleanarchitecturenoteapp.di.RepModule
import by.zharikov.cleanarchitecturenoteapp.feature_note.presentation.MainActivity
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
class NoteScreenTest {


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
                    composable(Screen.NoteScreen.route){
                        NoteScreen(navController = navController)
                    }
                }

            }
        }
    }


    @Test
    fun clickToggleOrderSection_isVisible(){
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

}