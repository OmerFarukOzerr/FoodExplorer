package com.example.foodapp.repository


import com.example.foodapp.model.*
import com.example.foodapp.util.Resource


interface ApiRepository {

    suspend fun getCategoriesFromAPi() : Resource<Category>

    suspend fun getAreasFromApi() : Resource<Area>

    suspend fun getRandomMealFromApi() : Resource<MealDetail>

    suspend fun getMealByFirstLatter(mealFirstLatter : String?) : Resource<MealDetail>

    suspend fun getMealsWithCategory(categoryName : String?) : Resource<Meal>

    suspend fun getMealsWithArea(areaName : String?) : Resource<Meal>

    suspend fun getMealsDetailFromApi(mealId : String?) : Resource<MealDetail>

    suspend fun getMealDetailByName(mealName : String?) : Resource<Meal>
}