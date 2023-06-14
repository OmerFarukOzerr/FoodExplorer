package com.example.foodapp.repository

import androidx.lifecycle.LiveData
import com.example.foodapp.database.Dao
import com.example.foodapp.model.Meal
import com.example.foodapp.model.Meals
import com.example.foodapp.model.MealsDetail
import com.example.foodapp.util.Constants.EMPTY_DB
import com.example.foodapp.util.Resource
import javax.inject.Inject

class DbRepo @Inject constructor(
    private val dao : Dao

) : DbRepository {
    override suspend fun getMealsDetailFromDb(mealdetailID: Int): Resource<MealsDetail> {
        return Resource.success(dao.takeMealsDetail(mealdetailID))
    }

    override suspend fun getMealFromDb(): Resource<Meal> {
        val response = dao.takeMeal()

        return if(response.isEmpty()) {
            Resource.error(EMPTY_DB, null)

        } else {
            Resource.success(Meal(response))
        }
    }

    override suspend fun insertMealsDetails(mealsDetail: MealsDetail) {
        dao.insertMealsDetails(mealsDetail)
    }


    override suspend fun insertMeal(meal: Meals) {
        dao.insertMeal(meal)
    }


    override suspend fun deleteMealsDetails(mealDetailID : Int) {
        dao.deleteMealsDetails(mealDetailID)
    }


    override suspend fun deleteMeal(mealID: Int) {
        dao.deleteMeal(mealID)
    }


    override fun checkFav(mealID: Int): LiveData<Boolean> {
        return dao.existsMealsDetail(mealID)
    }

}