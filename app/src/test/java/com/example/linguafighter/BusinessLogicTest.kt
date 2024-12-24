
import com.example.linguafighter.model.Question
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionTest {
    @Test
    fun `correct answer is validated`() {
        val question = Question("chat", listOf("dog", "cat", "elephant", "rabbit"), "cat")
        assertTrue("Answer should be valid", question.answer == "cat")
    }
}
