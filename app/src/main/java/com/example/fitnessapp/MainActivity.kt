package com.example.fitnessapp

import NewWorkoutScreen
import WorkoutLogScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.model.LoginScreenView
import com.example.fitnessapp.model.WorkoutViewModel
import com.example.fitnessapp.ui.LoginScreen
import com.example.fitnessapp.ui.theme.FitnessAppTheme

enum class FitnessAppScreensEnum(@StringRes val title: Int) {
    LOGIN(title = R.string.login_screen_title),
    PICK_EXERCISE(title = R.string.main_menu_title),
    START_WORKOUT(title = R.string.start_workout_title),
    EXERCISE_LOG(title = R.string.exercise_log_title),
    PREFERENCES(title = R.string.preferences_title),
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FitnessApp()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FitnessAppBar(
        currentScreen: FitnessAppScreensEnum,
        canNavigateBack: Boolean,
        navigateUp: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        TopAppBar(
            title = { Text(stringResource(currentScreen.title)) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FitnessApp() {
        val loginScreenViewModel: LoginScreenView = viewModel()
        val workoutScreenViewModel: WorkoutViewModel = viewModel()
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = FitnessAppScreensEnum.valueOf(backStackEntry?.destination?.route ?: FitnessAppScreensEnum.LOGIN.name)

        val uiState by loginScreenViewModel.uiState.collectAsState()
        Scaffold( topBar = {
            FitnessAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }) { innerPadding ->
            NavHost(navController, startDestination = FitnessAppScreensEnum.LOGIN.name) {
                composable(FitnessAppScreensEnum.LOGIN.name) {
                    LoginScreen(
                        onHelpButtonClicked = {
                            val intent = Intent(this@MainActivity, HelpActivity::class.java)
                            startActivity(intent)
                        },
                        onLoginButtonClicked = { user, password ->
                            loginScreenViewModel.onLoginButtonClick(user, password)
                            navController.navigate(FitnessAppScreensEnum.PICK_EXERCISE.name) {
                                popUpTo(navController.currentBackStackEntry!!.destination.route!!) {
                                    inclusive = true
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
                composable(FitnessAppScreensEnum.PICK_EXERCISE.name) {
                    ActivityPickerScreen(uiState,
                        onClickLogout = {
                            loginScreenViewModel.resetLogin()
                            navController.navigate(FitnessAppScreensEnum.LOGIN.name) {
                                popUpTo(navController.currentBackStackEntry!!.destination.route!!) {
                                    inclusive = true
                                }
                            }
                        },
                        onClickStartWorkout = {
                            navController.navigate(FitnessAppScreensEnum.START_WORKOUT.name)
                        },
                        onClickWorkoutLog = {
                            navController.navigate(FitnessAppScreensEnum.EXERCISE_LOG.name)
                        },
                        onClickPreferences = {
                            navController.navigate(FitnessAppScreensEnum.PREFERENCES.name)
                        }
                    )
                }
                composable(FitnessAppScreensEnum.START_WORKOUT.name) {
                    NewWorkoutScreen(workoutScreenViewModel,
                        onWorkoutFinishButtonClicked = {
                            navController.navigate(FitnessAppScreensEnum.PICK_EXERCISE.name) {
                                popUpTo(FitnessAppScreensEnum.PICK_EXERCISE.name) {
                                    inclusive = true
                                }
                            }
                        })
                }
                composable(FitnessAppScreensEnum.EXERCISE_LOG.name) {
                    WorkoutLogScreen()
                }
                composable(FitnessAppScreensEnum.PREFERENCES.name) {
                    PreferencesScreen()
                }
            }
        }
    }
}