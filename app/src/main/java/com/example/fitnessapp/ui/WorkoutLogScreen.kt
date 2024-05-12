import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessapp.data.SetInfo
import com.example.fitnessapp.data.WorkoutInfo
import com.example.fitnessapp.model.WorkoutViewModel

@Composable
fun WorkoutLogScreen(viewModel: WorkoutViewModel, modifier: Modifier = Modifier) {
    val workouts by viewModel.workouts.collectAsState()

    Surface(modifier = modifier.padding(75.dp).fillMaxSize()) {
        Text("Saved workouts will appear here", modifier = modifier.padding(16.dp))
    }

    Column(
        modifier = modifier
            .padding(top = 75.dp, bottom = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(workouts, key = { it.id }) { workout ->
                WorkoutCardShowInfoOnly(
                    workoutInfo = workout,
                )
            }
        }
    }
}

@Composable
fun WorkoutCardShowInfoOnly(
    workoutInfo: WorkoutInfo,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(workoutInfo.workoutName) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        WorkoutCardInfoViewOnly(sets = workoutInfo.sets)
    }
}

@Composable
fun WorkoutCardInfoViewOnly(sets: List<SetInfo>, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        sets.forEachIndexed { index, setInfo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Reps: ",
                        modifier = modifier.width(100.dp).height(50.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = setInfo.reps,
                        modifier = modifier.width(100.dp).height(50.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Left
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Weight: ",
                        modifier = modifier.width(100.dp).height(50.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = setInfo.weight,
                        modifier = modifier.width(100.dp).height(50.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Left
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutLogPreview() {
   /* Column(
        modifier = Modifier
            .padding(top = 75.dp, bottom = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.workouts, key = { it.id }) { workout ->
                WorkoutCard(
                    workoutInfo = workout,
                    onNameChange = { newName ->
                        viewModel.updateWorkoutName(workout.id, newName)
                    },
                    onRemove = {
                        viewModel.removeWorkout(workout.id)
                    }
                )
            }
        }
    }*/
}