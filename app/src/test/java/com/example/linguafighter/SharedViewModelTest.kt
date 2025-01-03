import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.linguafighter.model.Question
import com.example.linguafighter.ui.shared.viewmodel.SharedUiEvent
import com.example.linguafighter.ui.shared.viewmodel.SharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testQuestionSet = listOf(
        Question("chat", listOf("dog", "cat", "elephant", "rabbit"), "cat"),
        Question("maison", listOf("house", "tree", "river", "car"), "house")
    )

    private val viewModel = SharedViewModel()

    @Test
    fun `uploadQuestionSet updates the questionSet in uiState`() = runTest {
        viewModel.uploadQuestionSet(testQuestionSet)

        val state = viewModel.uiState.first()
        assertEquals(testQuestionSet, state.questionSet)
    }

    @Test
    fun `fetchQuestions returns the current questionSet`() = runTest {
        viewModel.uploadQuestionSet(testQuestionSet)

        val questions = viewModel.fetchQuestions()
        assertEquals(testQuestionSet, questions)
    }

    @Test
    fun `updateAnswer adds an answer to the answerSet`() = runTest {
        val question = testQuestionSet.first()
        viewModel.updateAnswer(
            option = "cat",
            currentQuestion = question,
            questionNumber = 1
        )

        val state = viewModel.uiState.first()
        assertEquals(1, state.answerSet.size)

        val answer = state.answerSet.first()
        assertEquals(question, answer.question)
        assertEquals("cat", answer.choice)
        assertEquals(1, answer.questionNumber)
    }

    @Test
     fun `discardAnswers clears the answerSet`() = runTest {
        val question = testQuestionSet.first()
        viewModel.updateAnswer(
            option = "cat",
            currentQuestion = question,
            questionNumber = 1
        )

        // Ensure the answerSet is not empty
        var state = viewModel.uiState.first()
        assertEquals(1, state.answerSet.size)

        // Discard answers
        viewModel.discardAnswers()
        state = viewModel.uiState.first()
        assertTrue(state.answerSet.isEmpty())
    }

    @Test
    fun `onEvent handles UploadQuestionSet correctly`() = runTest {
        viewModel.onEvent(SharedUiEvent.UploadQuestionSet(testQuestionSet))

        val state = viewModel.uiState.first()
        assertEquals(testQuestionSet, state.questionSet)
    }

    @Test
    fun `onEvent handles UpdateAnswer correctly`() = runTest {
        val question = testQuestionSet.first()
        viewModel.onEvent(
            SharedUiEvent.UpdateAnswer(
                option = "cat",
                currentQuestion = question,
                questionNumber = 1
            )
        )

        val state = viewModel.uiState.first()
        assertEquals(1, state.answerSet.size)

        val answer = state.answerSet.first()
        assertEquals(question, answer.question)
        assertEquals("cat", answer.choice)
        assertEquals(1, answer.questionNumber)
    }

    @Test
    fun `onEvent handles DiscardAnswers correctly`() = runTest {
        val question = testQuestionSet.first()
        viewModel.updateAnswer(
            option = "cat",
            currentQuestion = question,
            questionNumber = 1
        )

        // Ensure the answerSet is not empty
        var state = viewModel.uiState.first()
        assertEquals(1, state.answerSet.size)

        // Trigger DiscardAnswers event
        viewModel.onEvent(SharedUiEvent.DiscardAnswers)

        // Verify answerSet is empty
        state = viewModel.uiState.first()
        assertTrue(state.answerSet.isEmpty())
    }
}
