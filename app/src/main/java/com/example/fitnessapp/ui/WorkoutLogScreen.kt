import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnessapp.data.SetInfo
import com.example.fitnessapp.data.WorkoutInfo
import com.example.fitnessapp.model.WorkoutViewModel

@Composable
fun WorkoutLogScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.padding(75.dp).fillMaxSize()) {
        Text("Saved workouts will appear here", modifier = modifier.padding(16.dp))
    }

}


@Preview(showBackground = true)
@Composable
fun WorkoutLogPreview() {
    WorkoutLogScreen()
}