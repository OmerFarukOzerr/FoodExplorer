package com.example.foodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.model.Area
import com.example.foodapp.model.Category
import com.example.foodapp.model.MealDetail
import com.example.foodapp.repository.ApiRepository
import com.example.foodapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val apiRepository: ApiRepository

) : ViewModel(){

    private val _categories = MutableLiveData<Resource<Category>>()
       val categories : LiveData<Resource<Category>>
        get() = _categories


    private val _recommendedMeal = MutableLiveData<Resource<MealDetail>>()
    val recommendedMeal : LiveData<Resource<MealDetail>>
        get() = _recommendedMeal


    private val _area = MutableLiveData<Resource<Area>>()
    val area : LiveData<Resource<Area>>
        get() = _area


    fun takeCategories() {
        viewModelScope.launch {
            _categories.value = Resource.loading(null)
            val response = apiRepository.getCategoriesFromAPi()
            _categories.value = response
        }
    }

    fun takeMealByFirstLatter(mealFirstLatter : String?) {
        viewModelScope.launch {
            _categories.value = Resource.loading(null)
            val response = apiRepository.getMealByFirstLatter(mealFirstLatter)
            _recommendedMeal.value = response
        }
    }


    fun takeAreas() {
        viewModelScope.launch {
            _area.value = Resource.loading(null)
            val response = apiRepository.getAreasFromApi()
            _area.value = response
        }
    }

}