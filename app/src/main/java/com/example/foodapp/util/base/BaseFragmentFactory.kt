package com.example.foodapp.util.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.foodapp.view.CategoryFragment
import com.example.foodapp.view.MealDetailFragment
import com.example.foodapp.view.MealFragment
import com.example.foodapp.view.adapter.*
import com.example.foodapp.viewmodel.CategoryViewModel
import com.example.foodapp.viewmodel.MealDetailViewModel
import com.example.foodapp.viewmodel.MealViewModel
import javax.inject.Inject

class BaseFragmentFactory @Inject constructor(
    private val viewModel: CategoryViewModel,
    private val categoryAdapter: CategoryAdapter,
    private val areaAdapter : AreaAdapter,
    private val mealAdapter: MealAdapter,
    private val mealViewModel : MealViewModel,
    private val mealDetailViewModel: MealDetailViewModel,
    private val mealDetailAdapter: MealDetailAdapter,
    private val recMealAdapter: RecMealAdapter,
    private val networkHelper: NetworkHelper

) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
       return when(className) {

            CategoryFragment::class.java.name -> CategoryFragment(viewModel, categoryAdapter, areaAdapter, recMealAdapter)
            MealFragment::class.java.name -> MealFragment(mealAdapter, mealViewModel)
            MealDetailFragment::class.java.name -> MealDetailFragment(mealDetailViewModel, mealDetailAdapter, networkHelper)

            else -> super.instantiate(classLoader, className)
        }
    }

}