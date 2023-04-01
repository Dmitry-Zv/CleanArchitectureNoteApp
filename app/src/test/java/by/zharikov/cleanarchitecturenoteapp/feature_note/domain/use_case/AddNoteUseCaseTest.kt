package by.zharikov.cleanarchitecturenoteapp.feature_note.domain.use_case

import by.zharikov.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import by.zharikov.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddNoteUseCaseTest {

    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var fakeNoteRepository:FakeNoteRepository

    @Before
    fun setUp() {

        fakeNoteRepository =
            FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(fakeNoteRepository)
    }


    @Test
    fun `Enter blank title throw InvalidNoteException`() {
        val note = Note(
            title = " ",
            content = "content",
            timestamp = 1L,
            color = 1,
            id = 1
        )
        assertThrows(InvalidNoteException::class.java) {
            runTest {
                addNoteUseCase(note)

            }
        }
    }

    @Test
    fun `Enter blank content throw InvalidNoteException`() {
        val note = Note(
            title = "title",
            content = " ",
            timestamp = 1L,
            color = 1,
            id = 1
        )
        assertThrows(InvalidNoteException::class.java) {
            runTest {
                addNoteUseCase(note)
            }
        }
    }

    @Test
    fun `Enter not blank title and content, correct adding`() = runTest {
        val note = Note(
            title = "title",
            content = "content",
            timestamp = 1L,
            color = 1,
            id = 1
        )
        addNoteUseCase(note)
        assertThat(fakeNoteRepository.getNoteById(1)).isEqualTo(note)
    }


}