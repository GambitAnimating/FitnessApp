package com.example.fitnessapp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

data class WorkoutInfo(
    val id: UUID = UUID.randomUUID(),
    var workoutName: String,
    var sets: MutableList<SetInfo>
)
data class SetInfo(
    var reps: String,
    var weight: String
)