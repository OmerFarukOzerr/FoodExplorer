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
import com.example.foodapp.databinding.RowCategoryBinding
import com.example.foodapp.model.Categories
import com.example.foodapp.view.CategoryFragmentDirections
import javax.inject.Inject

class CategoryAdapter @Inject constructor(): RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {
    class CategoryVH(var view : RowCategoryBinding) : ViewHolder(view.root)

    private lateinit var binding : RowCategoryBinding


    private val differ = object : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem == newItem
        }
    }
    private val categoryDiffer = AsyncListDiffer(this, differ)


    var categories : List<Categories>
        get() = categoryDiffer.currentList
        set(value) = categoryDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.row_category, parent, false )

        return CategoryVH(binding)
    }


    override fun getItemCount() = categories.size


    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.view.categories = categories[position]
        holder.itemView.setOnClickListener {

            val action = CategoryFragmentDirections.actionCategoryFragmentToMealFragment(categories[position].strCategory, 1)
            Navigation.findNavController(holder.itemView).navigate(action)
        }


    }
}