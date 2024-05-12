package com.example.fitnessapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fitnessapp.FitnessApp
import com.example.fitnessapp.model.WorkoutViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Other Initializers
        // Initializer for ItemEntryViewModel
        initializer {
            WorkoutViewModel(inventoryApplication().applicationContext)
        }
        //...
    }
}
fun CreationExtras.inventoryApplication(): FitnessApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FitnessApp)