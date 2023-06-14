package com.example.foodapp.repository

import androidx.lifecycle.LiveData
import com.example.foodapp.model.Meal
import com.example.foodapp.model.Meals
import com.example.foodapp.model.MealsDetail
import com.example.foodapp.util.Resource

interface DbRepository {

    suspend fun getMealsDetailFromDb(mealdetailID : Int) : Resource<MealsDetail>

    suspend fun getMealFromDb() : Resource<Meal>

    suspend fun insertMealsDetails(mealsDetail: MealsDetail)

    suspend fun insertMeal(meal : Meals)

    suspend fun deleteMealsDetails(mealsDetailID : Int)

    suspend fun deleteMeal(mealID: Int)

    fun checkFav(mealID : Int) : LiveData<Boolean>


}