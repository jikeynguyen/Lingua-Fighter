package com.example.linguafighter.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linguafighter.ui.screens.answers.RevealAnswersScreen
import com.example.linguafighter.ui.screens.game.GameScreen
import com.example.linguafighter.ui.screens.start.StartScreen
import com.example.linguafighter.ui.shared.viewmodel.SharedViewModel


enum class linguafighterScreen() {
    Start,
    Game,
    ReviewAnswers
}

@Composable
fun linguafighterApp(
    sharedViewModel: SharedViewModel = viewModel(),
    gameViewModel : GameViewModel = viewModel(),
    navController : NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = linguafighterScreen.Start.name
    ) {
        composable(route = linguafighterScreen.Start.name ) {
            StartScreen(
                onSharedEvent = {event -> sharedViewModel.onEvent(event)},
                onConfirmation = {
                    gameViewModel.initializeGame(questionDataset = sharedViewModel.fetchQuestions())
                    navController.navigate(route = linguafighterScreen.Game.name)
                }
            )
        }

        composable(route =  linguafighterScreen.Game.name) {
            val gameUiState by gameViewModel.uiState.collectAsState()
            GameScreen(
                gameUiState = gameUiState,
                onEvent = { event -> gameViewModel.onEvent(event) },
                onSharedEvent = {event -> sharedViewModel.onEvent(event)},
                onExitPauseMenu = { navController.navigate(route = linguafighterScreen.Start.name) },
                onReviewAnswer = { navController.navigate(route = linguafighterScreen.ReviewAnswers.name) }
            )
        }

        composable(route = linguafighterScreen.ReviewAnswers.name) {
            val sharedUiState by sharedViewModel.uiState.collectAsState()
            RevealAnswersScreen(
                sharedUiState = sharedUiState,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}