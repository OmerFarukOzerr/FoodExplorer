package com.example.foodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.model.Meal
import com.example.foodapp.repository.ApiRepository
import com.example.foodapp.repository.DbRepository
import com.example.foodapp.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dbRepository: DbRepository

) : ViewModel() {

    private val _meals = MutableLiveData<Resource<Meal>>()
    val meal: LiveData<Resource<Meal>>
        get() = _meals


    fun takeMealsWithCategory(categoryName: String?) {
        viewModelScope.launch {
            _meals.value = Resource.loading( Meal(listOf()) )
            val response = apiRepository.getMealsWithCategory(categoryName)
            _meals.value = response
        }
    }

    fun takeMealsWithArea(areaName: String?) {
        viewModelScope.launch {
            _meals.value = Resource.loading( Meal(listOf()) )
            val response = apiRepository.getMealsWithArea(areaName)
            _meals.value = response
        }
    }

    fun takeMealsByName(mealName : String?) {
        if(mealName.isNullOrEmpty().not()) {
            viewModelScope.launch {
                _meals.value = Resource.loading( Meal(listOf()) )
                val response = apiRepository.getMealDetailByName(mealName)
                _meals.postValue(response)
            }
        }
    }

    fun takeMealFromDb() {
       viewModelScope.launch {
           _meals.value = Resource.loading( Meal(listOf()) )
           val meal = dbRepository.getMealFromDb()
           _meals.value = meal
       }
    }

    fun clearMeals() {
        viewModelScope.launch {
            _meals.value = Resource.loading( Meal(listOf()) )
            _meals.value = Resource.success( Meal(listOf()) )
        }
    }

}