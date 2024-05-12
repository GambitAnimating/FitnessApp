import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnessapp.data.SetInfo
import com.example.fitnessapp.data.WorkoutInfo
import com.example.fitnessapp.model.WorkoutViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

@Composable
fun NewWorkoutScreen(viewModel: WorkoutViewModel, onWorkoutFinishButtonClicked: () -> Unit = {}, modifier: Modifier = Modifier) {
    val workouts = viewModel.workouts
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
            items(workouts.value, key = { it.id }) { workout ->
                WorkoutCard(viewModel,
                    workoutInfo = workout,
                    onNameChange = { newName ->
                        viewModel.updateWorkoutName(workout.id, newName)
                    },
                    onRemove = {
                        viewModel.removeWorkout(workout.id)
                    }
                )
            }
            item {
                Button(
                    onClick = { viewModel.addWorkout() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Add Workout")
                }
            }
        }
        Button(
            onClick = {
                viewModel.saveWorkout()
                onWorkoutFinishButtonClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Text("Finish Workout")
        }
    }
}

@Composable
fun WorkoutCard(viewModel: WorkoutViewModel,
    workoutInfo: WorkoutInfo,
    onNameChange: (String) -> Unit,
    onRemove: () -> Unit,
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
            TextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                    onNameChange(newName)
                },
                label = { Text("Workout Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            WorkoutCardInfo(viewModel, workoutInfo.id, workoutInfo.sets)

            Button(
                onClick = onRemove,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text("Remove Workout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewWorkoutPreview() {
    //NewWorkoutScreen()
}

@Preview(showBackground = true)
@Composable
fun ExtraSettings() {
    //WorkoutCardInfo(sets = mutableListOf(SetInfo("10", "100"), SetInfo("12", "120")))
}

@Composable
fun WorkoutCardInfo(viewModel: WorkoutViewModel, workoutId: UUID, sets: List<SetInfo>, modifier: Modifier = Modifier) {
    val triggerRecomposition = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        sets.forEachIndexed() { index, setInfo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Reps: ")
                    OutlinedTextField(
                        value = setInfo.reps,
                        onValueChange = { newValue ->
                            viewModel.updateSetReps(workoutId, index, newValue)
                            triggerRecomposition.value = !triggerRecomposition.value
                        },
                        modifier = modifier.width(100.dp).height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Weight: ")
                    OutlinedTextField(
                        value = setInfo.weight,
                        onValueChange = { newValue ->
                            viewModel.updateSetWeight(workoutId, index, newValue)
                            triggerRecomposition.value = !triggerRecomposition.value
                        },
                        modifier = modifier.width(100.dp).height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }

        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    viewModel.addSet(workoutId)
                    triggerRecomposition.value = !triggerRecomposition.value
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Add Set")
            }
            if (sets.size >= 1) {
                Button(
                    onClick = {
                        viewModel.removeSet(workoutId, sets.size - 1)
                        triggerRecomposition.value = !triggerRecomposition.value
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Remove Set")
                }
            }
        }
    }
}