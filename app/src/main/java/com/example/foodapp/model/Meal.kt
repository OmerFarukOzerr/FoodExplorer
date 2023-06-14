package com.example.foodapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Meal(
    val meals : List<Meals>
)

@Entity("saved_meal")
data class Meals (
    val strMeal : String?,
    val strMealThumb : String?,
    val idMeal : String?,

    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int = 0
)
