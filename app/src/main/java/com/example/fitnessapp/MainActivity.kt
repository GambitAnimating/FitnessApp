package com.example.fitnessapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.model.LoginScreenView
import com.example.fitnessapp.ui.LoginScreen
import com.example.fitnessapp.ui.theme.FitnessAppTheme

enum class FitnessAppScreensEnum() {
    LOGIN,
    PICK_EXERCISE
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
    fun FitnessApp() {
        val viewModel: LoginScreenView = viewModel()
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = FitnessAppScreensEnum.valueOf(backStackEntry?.destination?.route ?: FitnessAppScreensEnum.LOGIN.name)

        val uiState by viewModel.uiState.collectAsState()
        Scaffold() { innerPadding ->
            NavHost(navController, startDestination = FitnessAppScreensEnum.LOGIN.name) {
                composable(FitnessAppScreensEnum.LOGIN.name) {
                    LoginScreen(
                        onLoginButtonClicked = { user, password ->
                            viewModel.onLoginButtonClick(user, password)
                            navController.navigate(FitnessAppScreensEnum.PICK_EXERCISE.name)
                        },
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
                composable(FitnessAppScreensEnum.PICK_EXERCISE.name) {
                    ActivityPickerScreen(uiState, onClickLogout = {
                        viewModel.resetLogin()
                        navController.popBackStack(
                            FitnessAppScreensEnum.LOGIN.name,
                            inclusive = false
                        )
                    })
                }
            }
        }
    }
}