package com.example.fitnessapp.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.data.SetInfo
import com.example.fitnessapp.data.WorkoutInfo
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.OutputStreamWriter
import java.util.UUID

open class WorkoutViewModel(private val context: Context) : ViewModel() {
    private val _workouts = MutableStateFlow<SnapshotStateList<WorkoutInfo>>(SnapshotStateList())
    val workouts: StateFlow<SnapshotStateList<WorkoutInfo>> = _workouts.asStateFlow()

    fun addWorkout() {
        _workouts.update { workouts ->
            workouts.add(WorkoutInfo(
                id = UUID.randomUUID(),
                workoutName = "New Workout Session ${workouts.size + 1}",
                sets = mutableStateListOf()
            ))
            workouts
        }
    }

    fun updateWorkoutName(id: UUID, newName: String) {
        _workouts.update { workouts ->
            workouts.find { it.id == id }?.workoutName = newName
            workouts
        }
    }

    fun addSet(id: UUID) {
        _workouts.update { workouts ->
            workouts.onEach { workout ->
                if (workout.id == id) {
                    workout.sets.also { it.add(SetInfo(reps = "", weight = "")) }
                }
            }
        }
    }
    fun updateSetReps(workoutId: UUID, setId: Int, newReps: String) {
        _workouts.update { workouts ->
            workouts.onEach { workout ->
                if (workout.id == workoutId) {
                    workout.sets.also { sets ->
                        sets[setId].reps = newReps
                        workout.sets = sets }
                }
            }
        }
    }

    fun updateSetWeight(workoutId: UUID, setId: Int, newWeight: String) {
        _workouts.update { workouts ->
            workouts.onEach { workout ->
                if (workout.id == workoutId) workout.sets[setId].weight = newWeight
            }
        }
    }

    fun removeSet(workoutId: UUID, setIndex: Int) {
        _workouts.update { workouts ->
            workouts.onEach { workout ->
                if (workout.id == workoutId) {
                    workout.sets.also { it.removeAt(setIndex) }
                }
            }
        }
    }

    fun removeWorkout(id: UUID) {
        _workouts.update { workouts ->
            workouts.removeAll { it.id == id }
            workouts
        }
    }

    fun saveWorkout() {
        val jsonString = workoutsToJson()

        context.openFileOutput("workouts.json", Context.MODE_PRIVATE).use {
            val outputWriter = OutputStreamWriter(it)
            outputWriter.write(jsonString)
            outputWriter.close()
        }

        // Clear the list of workouts
        _workouts.value.clear()
    }

    fun loadWorkouts() {
        val jsonString = context.openFileInput("workouts.json").bufferedReader().use {
            it.readText()
        }
        val gson = Gson()
        val workoutsArray = gson.fromJson(jsonString, Array<WorkoutInfo>::class.java)
        _workouts.update { workouts ->
            workouts.clear()
            workouts.addAll(workoutsArray)
            workouts
        }
    }

    fun workoutsToJson(): String {
        val gson = Gson()
        return gson.toJson(_workouts.value)
    }
}