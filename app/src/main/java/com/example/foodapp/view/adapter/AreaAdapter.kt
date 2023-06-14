package com.example.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.databinding.RowAreaBinding
import com.example.foodapp.model.Areas
import com.example.foodapp.view.CategoryFragmentDirections
import javax.inject.Inject

class AreaAdapter @Inject constructor() : RecyclerView.Adapter<AreaAdapter.AreaVH>() {
    class AreaVH(val view : RowAreaBinding) : RecyclerView.ViewHolder(view.root)

    private lateinit var binding: RowAreaBinding

    private val differ = object : DiffUtil.ItemCallback<Areas>() {
        override fun areItemsTheSame(oldItem: Areas, newItem: Areas): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: Areas, newItem: Areas): Boolean {
            return oldItem == newItem
        }
    }
    private val areaDiffer = AsyncListDiffer(this, differ)


    var areas : List<Areas>
        get() = areaDiffer.currentList
        set(value) = areaDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_area, parent, false)

        return AreaVH(binding)
    }

    override fun getItemCount() = areas.size


    override fun onBindViewHolder(holder: AreaVH, position: Int) {
        holder.view.area = areas[position]

        holder.itemView.setOnClickListener {
            val navController = Navigation.findNavController(holder.itemView)
            val action = CategoryFragmentDirections.actionCategoryFragmentToMealFragment(areas[position].strArea, 2)
            navController.popBackStack(R.id.categoryFragment, false)
            navController.navigate(action)

        }
    }
}