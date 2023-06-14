package com.example.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodapp.R
import com.example.foodapp.databinding.RowMealBinding
import com.example.foodapp.model.Meals
import com.example.foodapp.view.MealFragmentDirections
import javax.inject.Inject

class MealAdapter @Inject constructor() : RecyclerView.Adapter<MealAdapter.MealVH>() {
    class MealVH(var view : RowMealBinding) : ViewHolder(view.root)

    private lateinit var binder : RowMealBinding
    var dbFlag : Boolean = false


    private val differ = object : DiffUtil.ItemCallback<Meals>() {
        override fun areItemsTheSame(oldItem: Meals, newItem: Meals): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: Meals, newItem: Meals): Boolean {
            return oldItem == newItem
        }
    }
    private val mealDiffer = AsyncListDiffer(this, differ)


    var meals : List<Meals>
        get() = mealDiffer.currentList
        set(value) = mealDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binder = DataBindingUtil.inflate(layoutInflater, R.layout.row_meal, parent, false)

        return MealVH(binder)
    }

    override fun getItemCount() = meals.size


    override fun onBindViewHolder(holder: MealVH, position: Int) {
        holder.view.meals = meals[position]
        holder.itemView.setOnClickListener {
            meals[position].idMeal?.let {
                val action = MealFragmentDirections.actionMealFragmentToMealDetailFragment(it, dbFlag)
                Navigation.findNavController(holder.itemView).navigate(action) }
        }
    }


}