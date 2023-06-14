package com.example.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodapp.R
import com.example.foodapp.databinding.RowMealDetailBinding
import com.example.foodapp.model.IngredientAndMeasures
import javax.inject.Inject

class MealDetailAdapter @Inject constructor() : RecyclerView.Adapter<MealDetailAdapter.MealDetailVH>() {
    class MealDetailVH(var view : RowMealDetailBinding) : ViewHolder(view.root)

    private lateinit var binding : RowMealDetailBinding


    private val differ = object : DiffUtil.ItemCallback<IngredientAndMeasures>() {
        override fun areItemsTheSame(oldItem: IngredientAndMeasures, newItem: IngredientAndMeasures): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: IngredientAndMeasures, newItem: IngredientAndMeasures): Boolean {
            return oldItem == newItem
        }
    }
    private val mealDetailDiffer = AsyncListDiffer(this , differ)


    var ingredientAndMeasures : List<IngredientAndMeasures>
        get() = mealDetailDiffer.currentList
        set(value) = mealDetailDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealDetailVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_meal_detail, parent, false)

        return MealDetailVH(binding)
    }


    override fun getItemCount() = ingredientAndMeasures.size


    override fun onBindViewHolder(holder: MealDetailVH, position: Int) {
        holder.view.ingAndMea = ingredientAndMeasures[position]

    }


}