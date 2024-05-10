package com.example.fitnessapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.model.LoginUIState

@Composable
fun ActivityPickerScreen(
    loginUIState: LoginUIState,
    modifier: Modifier = Modifier,
    onClickLogout: () -> Unit,
    onClickStartWorkout: () -> Unit,
    onClickWorkoutLog: () -> Unit,
    onClickPreferences: () -> Unit
) {
    val username = loginUIState.username
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Welcome $username",
                modifier = Modifier.padding(
                    horizontal = 0.dp,
                    vertical = 3.dp),
                style = MaterialTheme.typography.displaySmall,
                color = Color.Black,
                fontWeight = FontWeight.Black,
            )
            Text(
                "Please choose your workout activity.",
                modifier = Modifier.padding(
                    horizontal = 0.dp,
                    vertical = 3.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Black,
            )
        }
        Button(
            onClick = onClickStartWorkout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Start A New Workout")
        }
        Button(
            onClick = onClickWorkoutLog,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Workout Log")
        }

        Button(
            onClick = { onClickPreferences() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Settings")
        }
        Button(
            onClick = onClickLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Logout")
        }
    }
}

@Preview
@Composable
fun ActivityPickerPreview() {
    ActivityPickerScreen(
        loginUIState = LoginUIState("Jed"),
        onClickLogout = {},
        onClickStartWorkout = {},
        onClickWorkoutLog = {},
        onClickPreferences = {},
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    )
}