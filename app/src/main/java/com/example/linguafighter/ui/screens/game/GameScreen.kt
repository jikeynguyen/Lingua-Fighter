package com.example.linguafighter.ui.screens.game

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.linguafighter.model.Question
import com.example.linguafighter.ui.theme.linguafighterTheme
import com.example.linguafighter.ui.shared.components.QuestionView
import com.example.linguafighter.ui.shared.viewmodel.SharedUiEvent

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameUiState: GameUiState,
    onEvent : (GameUiEvent) -> Unit,
    onSharedEvent : (SharedUiEvent) -> Unit,
    onExitPauseMenu : () -> Unit,
    onReviewAnswer : () -> Unit,
) {
    var isPause by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                questionNumber = gameUiState.questionNumber,
                time = gameUiState.time,
                score = gameUiState.currentScore,
                onPause = {
                    onEvent(GameUiEvent.PauseTimer)
                    isPause = !isPause
                }
            )
        }
    ) {
        paddingValues ->

        QuestionView(
            word = gameUiState.currentQuestion.word,
            options = gameUiState.currentQuestion.options,
            canClick = gameUiState.canClick,
            onOptionSelected = {
                onEvent(
                    GameUiEvent.CheckUserAnswer(it)
                )
                onSharedEvent(SharedUiEvent.UpdateAnswer(
                    option = it,
                    currentQuestion = gameUiState.currentQuestion,
                    questionNumber = gameUiState.questionNumber
                )) },
            answer = gameUiState.currentQuestion.answer,
            selected = gameUiState.selected,
            modifier = Modifier.padding(paddingValues)
        )

        if (isPause) {
            PauseMenu(
                onResumeQuiz = {
                    onEvent(GameUiEvent.ResumeTimer)
                    isPause = false },
                onExit = {
                    isPause = false
                    onExitPauseMenu()
                    onSharedEvent(SharedUiEvent.DiscardAnswers)
                },
                onNewTrivia = {
                    isPause = false
                    onEvent(GameUiEvent.UpdatQuestionSet)
                    onSharedEvent(SharedUiEvent.DiscardAnswers)
                }
            )
        }
        
        if (gameUiState.quizEnd) {
            ResultDialog(
                score = gameUiState.currentScore,
                answeredCorrectly = gameUiState.answeredCorrectly,
                replayTrivia = {
                    onEvent(GameUiEvent.UpdatQuestionSet)
                    onSharedEvent(SharedUiEvent.DiscardAnswers)
                               },
                revealAnswers = onReviewAnswer,
                onExit = {
                    onExitPauseMenu()
                    onSharedEvent(SharedUiEvent.DiscardAnswers)
                })
        }

        if (gameUiState.timerEnd) {
            onSharedEvent(SharedUiEvent.UpdateAnswer(
                currentQuestion = gameUiState.currentQuestion,
                questionNumber = gameUiState.questionNumber
            ))
        }
    }
}

@Preview
@Composable
fun GameScreenLightThemePreview() {
    linguafighterTheme {
        GameScreen(
            gameUiState = GameUiState(
                currentQuestion = Question(
                    word = "chat",
                    options = listOf("cat", "dog", "lion", "rat")
                )
            ),
            onEvent = {},
            onSharedEvent = {} ,
            onExitPauseMenu = {},
            onReviewAnswer = {}
        )
    }
}

@Preview
@Composable
fun GameScreenDarkThemePreview() {
    linguafighterTheme(
        darkTheme = true
    ) {
        GameScreen(
            gameUiState = GameUiState(
                currentQuestion = Question(
                    word = "chat",
                    options = listOf("cat", "dog", "lion", "rat")
                )
            ),
            onEvent = {},
            onSharedEvent = {} ,
            onExitPauseMenu = {},
            onReviewAnswer = {}
        )

    }
}
