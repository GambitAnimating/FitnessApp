package com.example.fitnessapp

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PreferencesScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("PREFERENCES_NAME", Context.MODE_PRIVATE)
    var showNotifications by remember { mutableStateOf(sharedPreferences.getBoolean("showNotifications", false)) }

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier.padding(vertical = 75.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text("Show Notifications: ", fontWeight = FontWeight.Black, style = MaterialTheme.typography.bodyLarge)

            Switch(
                checked = showNotifications,
                onCheckedChange = { isChecked ->
                    showNotifications = isChecked
                    with(sharedPreferences.edit()) {
                        putBoolean("showNotifications", isChecked)
                        apply()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreferencesScreenPreview() {
    PreferencesScreen()
}