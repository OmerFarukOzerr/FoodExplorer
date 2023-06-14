package com.example.foodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.model.*
import com.example.foodapp.repository.ApiRepository
import com.example.foodapp.repository.DbRepository
import com.example.foodapp.util.Resource
import com.example.foodapp.util.toMeals
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

class MealDetailViewModel @Inject constructor(
    private val apiRepository : ApiRepository,
    private val dbRepository : DbRepository

): ViewModel() {

    private val _mealDetail = MutableLiveData<Resource<MealDetail>>()
    val mealDetail : LiveData<Resource<MealDetail>>
        get() = _mealDetail


    private val _mealDetailDb = MutableLiveData<Resource<MealsDetail>>()
    val mealDetailDb : LiveData<Resource<MealsDetail>>
        get() = _mealDetailDb


    fun getMealDetailFromDb(mealsDetailID: Int) {
        viewModelScope.launch {
            _mealDetailDb.value = Resource.loading(null)
            val mealDetails = dbRepository.getMealsDetailFromDb(mealsDetailID)
            _mealDetailDb.value = mealDetails
        }
    }

    fun getMealDetails(mealId : String?) {
        mealId?.let {
            viewModelScope.launch {
                _mealDetail.value = Resource.loading(null)
                val response = apiRepository.getMealsDetailFromApi(mealId)
                _mealDetail.value = response
            }
        }
    }

    fun insertMealsDetail(mealsDetail: MealsDetail) {
        viewModelScope.launch { dbRepository.insertMealsDetails(mealsDetail) }
    }


    fun deleteMealsDetail(mealsDetailID : Int) {
        viewModelScope.launch { dbRepository.deleteMealsDetails(mealsDetailID) }
    }


    fun deleteMeals (mealID: Int) {
        viewModelScope.launch { dbRepository.deleteMeal(mealID) }
    }


    fun checkFav(mealID : Int) : LiveData<Boolean>  {
        return dbRepository.checkFav(mealID)
    }


    fun insertMeals(mealsDetail: MealsDetail) {
        viewModelScope.launch {
            mealsDetail.let {
                dbRepository.insertMeal(it.toMeals())
            }
        }
    }

    fun makeIngAndMeaList(mealsDetail : MealsDetail) : List<IngredientAndMeasures> {
        val ingSubList : ArrayList<String> = arrayListOf()
        val meaSubList : ArrayList<String> = arrayListOf()
        val ingredientAndMeasures : ArrayList<IngredientAndMeasures> = arrayListOf()

        MealsDetail::class.memberProperties.forEach {member->
            val name = member.name
            member.get(mealsDetail).takeIf {
                name.startsWith("i") && it.toString().isNotBlank() }?.let { ingSubList.add(it.toString()) }

            member.get(mealsDetail).takeIf {
                name.startsWith("m") && it.toString().isNotBlank() }?.let { meaSubList.add(it.toString()) }
        }
        val subMap : Map<String, String> = ingSubList.zip(meaSubList).toMap()
        subMap.forEach { (i, m) -> ingredientAndMeasures.add(IngredientAndMeasures(i,m)) }

        return ingredientAndMeasures
    }

}