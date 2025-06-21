package com.example.fitnessapp.data

import android.content.Context
import com.example.fitnessapp.loadCaloriesFromSharedPreferences

class FitnessData {
    companion object {
        var caloriesBurnedToday: Int = 0
        fun initialize(context: Context) {
            caloriesBurnedToday = loadCaloriesFromSharedPreferences(context)
        }
    }
}
