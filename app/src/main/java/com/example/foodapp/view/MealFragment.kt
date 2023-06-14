package com.example.foodapp.view


import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentMealBinding
import com.example.foodapp.util.Constants
import com.example.foodapp.util.Constants.EMPTY_DB
import com.example.foodapp.util.Constants.NO_SEARCH_RESPONSE
import com.example.foodapp.util.Status
import com.example.foodapp.view.adapter.MealAdapter
import com.example.foodapp.viewmodel.MealViewModel
import javax.inject.Inject


class MealFragment @Inject constructor(
    private val adapter : MealAdapter,
    private val viewModel : MealViewModel,

) : Fragment() {
    private var _binding : FragmentMealBinding? = null
    private val binding : FragmentMealBinding get() = _binding!!
    private var keyName : String? = null
    private var uuID : Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMealBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mealRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.mealRecyclerView.adapter = adapter

        dataSelection()

        if(uuID == 3) { (activity as AppCompatActivity).setSupportActionBar(binding.backToolbar) }

        binding.detailBackImage.setOnClickListener { findNavController().popBackStack() }

        findNavController().addOnDestinationChangedListener {c, d, a ->
            if(d.id == R.id.categoryFragment) { viewModel.clearMeals() }
        }
    }


    private fun dataSelection() {
        arguments?.let {
            keyName = MealFragmentArgs.fromBundle(it).keyName
            uuID = MealFragmentArgs.fromBundle(it).uuID
        }

        adapter.dbFlag = false

        getMeals()

        when(uuID) {
            0-> {
                binding.backBarTitle.setText(R.string.favorite)
                viewModel.takeMealFromDb()
                adapter.dbFlag = true
            }

            1-> {
                binding.backBarTitle.text = keyName
                viewModel.takeMealsWithCategory(keyName)
            }

            2 -> {
                binding.backBarTitle.text = keyName
                viewModel.takeMealsWithArea(keyName)
            }

            3 -> {
                binding.searchView.visibility = View.VISIBLE
                binding.backBarTitle.visibility = View.GONE
                getMealsByName()
            }
        }

    }

    private fun getMeals() {
        viewModel.meal.observe(viewLifecycleOwner, Observer {meal->
            when(meal.status) {
                Status.SUCCESS-> {
                    meal.data?.meals?.let { adapter.meals = it }
                    hideLoading()
                }

                Status.ERROR-> {
                    hideLoading()
                    noConnection( meal.message == Constants.NO_NETWORK_CONNECTION)
                   // networkError( meal.message == Constants.OTHER_ERROR)
                    if(meal.message == EMPTY_DB && uuID == 0) { showErrorMassage(EMPTY_DB) }
                    if(meal.message == NO_SEARCH_RESPONSE) { showErrorMassage(NO_SEARCH_RESPONSE) }
                }

                Status.LOADING-> {
                    showLoading()
                    meal.data?.meals?.let { adapter.meals = it }
                }
            }
       })
    }

    private fun getMealsByName() {
        val searchView = binding.searchView
        searchView.requestFocusFromTouch()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(p0.isNullOrEmpty().not()) {
                    viewModel.takeMealsByName(p0)
                    searchView.clearFocus()
                }

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }


    private fun showLoading() {
        binding.apply {
            if (uuID != 3) {
               shimmerFrameLayout.startShimmerAnimation()
               mealLay.visibility = View.GONE
               shimmerFrameLayout.visibility = View.VISIBLE

            } else {
                progressAnimation.setAnimation(R.raw.progress)
                progressAnimation.playAnimation()
                progressLay.visibility = View.VISIBLE
            }
        }
    }


    private fun hideLoading() {
        binding.apply {
            if(uuID != 3) {
               shimmerFrameLayout.stopShimmerAnimation()
               shimmerFrameLayout.visibility = View.GONE
               mealLay.visibility = View.VISIBLE

            } else {
                progressLay.visibility = View.GONE
            }
        }
    }


    private fun noConnection(errorFlag: Boolean) {
        binding.apply {
            if (errorFlag) {
                mealLay.visibility = View.GONE
                mealErrorDisplay.visibility = View.VISIBLE
                shimmerFrameLayout.visibility = View.GONE
                disconnectLay.imgDisconnect.setAnimation(R.raw.nointernet)
                disconnectLay.imgDisconnect.playAnimation()

            } else {
                mealLay.visibility = View.VISIBLE
                mealErrorDisplay.visibility = View.GONE
            }
        }
    }


    private fun networkError(errorFlag: Boolean) {
        binding.apply {
            if (errorFlag) {
                mealLay.visibility = View.GONE
                mealErrorDisplay.visibility = View.VISIBLE
                shimmerFrameLayout.visibility = View.GONE
                disconnectLay.imgDisconnect.setAnimation(R.raw.error)
                disconnectLay.imgDisconnect.playAnimation()

            } else {
                mealLay.visibility = View.VISIBLE
                mealErrorDisplay.visibility = View.GONE
            }
        }
    }


    private fun showErrorMassage(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()
        _binding = null
    }

}