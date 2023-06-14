package com.example.foodapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodapp.R
import com.example.foodapp.model.Meals
import com.example.foodapp.model.MealsDetail
import kotlin.reflect.full.memberProperties

fun ImageView.DownloadFromUrl(url : String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.placeholder)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}


fun placeHolderProgressBar(context : Context) : CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 3f
        centerRadius = 19f
        start()
    }
}

fun MealsDetail.toMeals() = with(::Meals) {
    val propertiesByName = MealsDetail::class.memberProperties.associateBy { it.name }

    callBy(args = parameters.associate { parameter ->
        parameter to when (parameter.name) {
            "strMeal" -> strMeal
            "strMealThumb" -> strMealThumb
            "idMeal" -> IDmeal
            else -> propertiesByName[parameter.name]?.get(this@toMeals)
        }
    })
}


@BindingAdapter("android:bindWithDownloadUrl")
fun bindWithDownloadUrl(view : ImageView, url : String?) {
    view.DownloadFromUrl(url, placeHolderProgressBar(view.context))
}

