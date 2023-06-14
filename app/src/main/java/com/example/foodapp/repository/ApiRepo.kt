package com.example.foodapp.repository


import androidx.lifecycle.LiveData
import com.example.foodapp.database.Dao
import com.example.foodapp.model.*
import com.example.foodapp.service.FoodApi
import com.example.foodapp.util.Constants.NO_NETWORK_CONNECTION
import com.example.foodapp.util.Constants.NO_SEARCH_RESPONSE
import com.example.foodapp.util.Constants.NULL_DATA
import com.example.foodapp.util.Constants.OTHER_ERROR
import com.example.foodapp.util.Resource
import com.example.foodapp.util.base.NetworkHelper
import com.example.foodapp.util.toMeals
import javax.inject.Inject

class  ApiRepo @Inject constructor(
    private val retrofit : FoodApi,
    private val networkHelper: NetworkHelper

) : ApiRepository {


    override suspend fun getCategoriesFromAPi(): Resource<Category> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.takeCategory()

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }


    override suspend fun getMealsWithCategory(categoryName : String?): Resource<Meal> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.takeMealWithCategory(categoryName)

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }

    override suspend fun getMealsWithArea(areaName: String?): Resource<Meal> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.takeMealWithArea(areaName)

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }


    override suspend fun getMealsDetailFromApi(mealId: String?): Resource<MealDetail> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.takeMealDetail(mealId)

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }

    override suspend fun getMealDetailByName(mealName : String?): Resource<Meal> {

        return if(networkHelper.isNetworkConnected()) {
            Resource.loading( Meal(listOf()) )
            val response = retrofit.takeMealDetailByName(mealName)

            if (response.isSuccessful) {
                response.body()?.let {mealDetail->
                    val meal = Meal(mealDetail.meals?.map { it.toMeals() } ?: listOf() )

                    if (meal.meals.isEmpty()) {
                        Resource.error(NO_SEARCH_RESPONSE, null)

                    } else {
                        return@let Resource.success(meal)
                    }
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }



    override suspend fun getAreasFromApi(): Resource<Area> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.takeAreas()

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }

    }

    override suspend fun getRandomMealFromApi(): Resource<MealDetail> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.takeRandomMeal()

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }

    override suspend fun getMealByFirstLatter(mealFirstLatter : String?): Resource<MealDetail> {

        return if(networkHelper.isNetworkConnected()) {
            val response = retrofit.takeMealsByFirstLat(mealFirstLatter)

            if (response.isSuccessful) {
                response.body()?.let {

                    return@let Resource.success(it)
                } ?: Resource.error(NULL_DATA, null)

            } else {
                Resource.error(OTHER_ERROR, null)
            }

        } else {
            Resource.error(NO_NETWORK_CONNECTION, null)
        }
    }

}