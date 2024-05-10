package com.example.fitnessapp.ui

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HelpScreen(onClickBack: () -> Unit = {},
               modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Button(onClick = { onClickBack() },
            modifier = Modifier.align(Alignment.TopStart), colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) {
            Text("Back", fontWeight = FontWeight.Black, style = MaterialTheme.typography.bodyLarge)
        }
        Column(
            modifier = modifier.fillMaxWidth().align(Alignment.TopCenter).padding(vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("About App", style = MaterialTheme.typography.headlineSmall)
            Text("This app is designed to help you track your fitness progress.", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.padding(16.dp))
            Text("About Preferences", style = MaterialTheme.typography.headlineSmall)
            Text("After logging in, in the preferences screen you can setup if you'd like notifications or not.", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun HelpScreenPreview() {
    HelpScreen()
}