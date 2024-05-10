package com.example.fitnessapp.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.data.WorkoutInfo
import java.util.UUID

open class WorkoutViewModel : ViewModel() {
    val workouts = mutableStateListOf<WorkoutInfo>()

    fun addWorkout() {
        workouts.add(WorkoutInfo(
            id = UUID.randomUUID(),
            workoutName = "New Workout Session ${workouts.size + 1}",
            sets = mutableListOf()
        ))
    }

    fun updateWorkoutName(id: UUID, newName: String) {
        workouts.find { it.id == id }?.let {
            it.workoutName = newName
        }
    }

    fun removeWorkout(id: UUID) {
        workouts.removeIf { it.id == id }
    }

    fun saveWorkout() {
        //TODO: Save the workout to the database

        // Clear the list of workouts
        workouts.clear()
    }
}