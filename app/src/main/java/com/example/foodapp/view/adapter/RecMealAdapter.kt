package com.example.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodapp.R
import com.example.foodapp.databinding.RecommendedMealBinding
import com.example.foodapp.model.MealsDetail
import com.example.foodapp.view.CategoryFragmentDirections
import kotlinx.coroutines.Runnable
import javax.inject.Inject

class RecMealAdapter @Inject constructor() : RecyclerView.Adapter<RecMealAdapter.RecMealVH>() {
    class RecMealVH(view : RecommendedMealBinding) : ViewHolder(view.root)

    lateinit var binding : RecommendedMealBinding


    private val differ = object : DiffUtil.ItemCallback<MealsDetail>() {
        override fun areItemsTheSame(oldItem: MealsDetail, newItem: MealsDetail): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: MealsDetail, newItem: MealsDetail): Boolean {
            return oldItem == newItem
        }
    }
    private val mealDiffer = AsyncListDiffer(this, differ)


    var mealDetails : List<MealsDetail>
        get() = mealDiffer.currentList
        set(value) = mealDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecMealVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.recommended_meal, parent, false)

        return RecMealVH(binding)
    }

    override fun getItemCount() = mealDetails.size


    override fun onBindViewHolder(holder: RecMealVH, position: Int) {
        binding.recommended = mealDetails[position]

        holder.itemView.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToMealDetailFragment(
                mealDetails[position].IDmeal.toString(), false)
            holder.itemView.findNavController().navigate(action)
        }
    }


}