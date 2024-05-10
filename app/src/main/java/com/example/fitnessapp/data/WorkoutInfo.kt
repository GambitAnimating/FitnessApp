package com.example.fitnessapp.data

import java.util.UUID

data class WorkoutInfo(
    val id: UUID = UUID.randomUUID(),
    var workoutName: String,
    val sets: MutableList<SetInfo>
)
data class SetInfo(
    var reps: String,
    var weight: String
)