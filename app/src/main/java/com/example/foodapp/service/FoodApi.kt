package com.example.foodapp.service

import com.example.foodapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("/api/json/v1/1/categories.php")
    suspend fun takeCategory() : Response<Category>


    @GET("/api/json/v1/1/list.php?a=list")
    suspend fun takeAreas() : Response<Area>


    @GET("/api/json/v1/1/filter.php?")
    suspend fun takeMealWithCategory(
        @Query ("c") categoryName : String?) : Response<Meal>


    @GET("/api/json/v1/1/lookup.php?")
    suspend fun takeMealDetail(
        @Query("i") mealId : String?) : Response<MealDetail>


    @GET("/api/json/v1/1/random.php")
    suspend fun takeRandomMeal() : Response<MealDetail>


    @GET("/api/json/v1/1/filter.php?")
    suspend fun takeMealWithArea(
        @Query ("a") categoryName : String?) : Response<Meal>


    @GET("/api/json/v1/1/search.php?")
    suspend fun takeMealDetailByName(
        @Query ("s") mealName : String?) : Response<MealDetail>


    @GET("/api/json/v1/1/search.php?")
    suspend fun takeMealsByFirstLat(
        @Query ("f") mealFirstLatter : String?) : Response<MealDetail>
}