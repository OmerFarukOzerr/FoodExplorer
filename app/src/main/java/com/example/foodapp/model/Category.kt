package com.example.foodapp.model

import com.google.gson.annotations.SerializedName


data class Category(
    val categories : List<Categories>
)

data class Categories(
    val idCategory: String?,
    val strCategory: String?,
    val strCategoryThumb: String?,
    val strCategoryDescription: String?,
)

data class Area (
    @SerializedName("meals") val area : ArrayList<Areas> = arrayListOf()
)

data class Areas (
    val strArea : String?
)

