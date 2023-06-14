package com.example.foodapp.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapp.model.Meals
import com.example.foodapp.model.MealsDetail
import com.example.foodapp.util.Constants.MEALS_DETAILS_TABLE
import com.example.foodapp.util.Constants.MEALS_TABLE

@androidx.room.Dao
interface Dao {

    @Query("DELETE FROM $MEALS_DETAILS_TABLE WHERE IDmeal IN (:mealDetailID)" )
    suspend fun deleteMealsDetails(mealDetailID : Int)

    @Query("DELETE FROM $MEALS_TABLE WHERE idMeal IN (:mealID)" )
    suspend fun deleteMeal(mealID: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealsDetails(mealsDetail: MealsDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meals: Meals)

    @Query("SELECT * FROM $MEALS_DETAILS_TABLE  WHERE IDmeal IN (:mealDetailID)")
    suspend fun takeMealsDetail(mealDetailID : Int) : MealsDetail

    @Query("SELECT * FROM $MEALS_TABLE")
    suspend fun takeMeal() : List<Meals>

    @Query("SELECT EXISTS (SELECT 1 FROM $MEALS_DETAILS_TABLE WHERE IDmeal = :id)")
    fun existsMealsDetail(id: Int): LiveData<Boolean>
}