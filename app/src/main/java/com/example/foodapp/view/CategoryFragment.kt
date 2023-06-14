package com.example.foodapp.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCategoryBinding
import com.example.foodapp.util.Constants
import com.example.foodapp.util.Constants.NO_NETWORK_CONNECTION
import com.example.foodapp.util.Constants.REFRESH_TRUE
import com.example.foodapp.util.Status
import com.example.foodapp.view.adapter.AreaAdapter
import com.example.foodapp.view.adapter.CategoryAdapter
import com.example.foodapp.view.adapter.RecMealAdapter
import com.example.foodapp.viewmodel.CategoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class CategoryFragment @Inject constructor(
    private val viewModel : CategoryViewModel,
    private val categoryAdapter : CategoryAdapter,
    private val areaAdapter: AreaAdapter,
    private val recMealAdapter: RecMealAdapter

) : Fragment() {
    private var _binding : FragmentCategoryBinding? = null
    private val binding : FragmentCategoryBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryRecyclerView.adapter = categoryAdapter
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2,
            GridLayoutManager.HORIZONTAL, false)

        toolbarMenu()

        viewPager2()

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.takeCategories()
        viewModel.takeMealByFirstLatter("e")
        viewModel.takeAreas()

        viewModel.categories.observe(viewLifecycleOwner, Observer {response->
            when(response.status) {
                Status.SUCCESS-> {
                    response.data?.let {
                        categoryAdapter.categories = it.categories
                        binding.categories = it.categories[(5..13).random()]
                    }
                    hideLoading()
                }

                Status.ERROR-> {
                    hideLoading()
                    noConnection( response.message == NO_NETWORK_CONNECTION )
                  //  networkError( response.message == OTHER_ERROR )
                }

                Status.LOADING-> {
                    showLoading()
                }
            }
        })

        viewModel.recommendedMeal.observe(viewLifecycleOwner, Observer {meal->
            when(meal.status) {
                Status.SUCCESS-> {
                    meal.data?.meals?.let {
                        recMealAdapter.mealDetails = it
                    }
                    hideLoading()
                }

                Status.ERROR-> {
                    noConnection( meal.message == NO_NETWORK_CONNECTION )
                  //  networkError( meal.message == OTHER_ERROR )
                }

                Status.LOADING-> {
                    showLoading()
                }
            }
        })

        viewModel.area.observe(viewLifecycleOwner, Observer {area->
            when(area.status) {
                Status.SUCCESS-> {
                    area.data?.let {
                        binding.areaRecyclerView.apply {
                            adapter = areaAdapter
                            val layoutManager = GridLayoutManager(requireContext(), 2,
                                GridLayoutManager.HORIZONTAL, false)
                            this.layoutManager = layoutManager
                        }
                        areaAdapter.areas = it.area
                        hideLoading()
                    }
                }

                Status.ERROR-> {
                    hideLoading()
                    noConnection( area.message == Constants.NO_NETWORK_CONNECTION)
                    //  networkError( area.message == Constants.OTHER_ERROR)
                }

                Status.LOADING-> {
                    showLoading()
                }

            }
        })
    }

    private fun toolbarMenu() {
        binding.homeFav.setOnClickListener {
            val navController = findNavController()
            navController.popBackStack(R.id.categoryFragment, false)
            val action = CategoryFragmentDirections.actionCategoryFragmentToMealFragment(
                "", 0)
            navController.navigate(action)
        }

        binding.homeSearch.setOnClickListener {
            val navController = findNavController()
            navController.popBackStack(R.id.categoryFragment, false)
            val action = CategoryFragmentDirections.actionCategoryFragmentToMealFragment(
                REFRESH_TRUE, 3)
            navController.navigate(action)
        }
    }

    private fun viewPager2() {
        binding.homeRecViewPager.apply {
            adapter = recMealAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.homePagerDot.attachTo(this)
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((10 * Resources.getSystem().displayMetrics.density).toInt()))
        binding.homeRecViewPager.setPageTransformer(compositePageTransformer)
    }


    private fun showLoading() {
        binding.homeAppbar.visibility = View.GONE
        binding.homeShimmer.startShimmerAnimation()
        binding.homeShimmer.visibility = View.VISIBLE
        binding.categoryLay.visibility = View.GONE
    }


    private fun hideLoading() {
       lifecycleScope.launch {
           delay(300)
           binding.homeAppbar.visibility = View.VISIBLE
           binding.homeShimmer.stopShimmerAnimation()
           binding.homeShimmer.visibility = View.GONE
           binding.categoryLay.visibility = View.VISIBLE
       }
    }

    private fun noConnection(errorFlag: Boolean) {
        binding.apply {
            if (errorFlag) {
                categoryLay.visibility = View.GONE
                homeShimmer.visibility = View.GONE
                homeNoConnection.visibility = View.VISIBLE
                disconnectLay.imgDisconnect.setAnimation(R.raw.nointernet)
                disconnectLay.imgDisconnect.playAnimation()

            } else {
                categoryLay.visibility = View.VISIBLE
                homeNoConnection.visibility = View.GONE
            }
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}