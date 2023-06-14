package com.example.foodapp.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentMealDetailBinding
import com.example.foodapp.model.MealsDetail
import com.example.foodapp.util.Constants
import com.example.foodapp.util.Status
import com.example.foodapp.util.base.NetworkHelper
import com.example.foodapp.view.adapter.MealDetailAdapter
import com.example.foodapp.viewmodel.MealDetailViewModel
import javax.inject.Inject


class MealDetailFragment @Inject constructor(
    private val viewModel : MealDetailViewModel,
    private val adapter : MealDetailAdapter,
    private val networkHelper: NetworkHelper

): Fragment() {
    private var _binding : FragmentMealDetailBinding? = null
    private val binding : FragmentMealDetailBinding get() = _binding!!
    private lateinit var mealID : String
    private var dbFlag : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View? {
        _binding = FragmentMealDetailBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mealRecyclerView.adapter = adapter
        binding.mealRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        arguments?.let {
            mealID = MealDetailFragmentArgs.fromBundle(it).mealID
            dbFlag = MealDetailFragmentArgs.fromBundle(it).dbFlag

            if(dbFlag) {
                takeMealsDetailFromDb()

            } else {
                getMealDetailFromApi()
            }
        }

        binding.detailBack.setOnClickListener { findNavController().popBackStack() }
    }


    private fun getMealDetailFromApi() {
        viewModel.getMealDetails(mealID)
        viewModel.mealDetail.observe(viewLifecycleOwner, Observer {response->
            when(response.status) {
                Status.SUCCESS-> {
                    response.data?.meals?.let {mealsDetails ->
                        mealsDetails.map {mealsDetail->
                            val ingredientAndMeasures = viewModel.makeIngAndMeaList(mealsDetail)
                            adapter.ingredientAndMeasures = ingredientAndMeasures
                            binding.mealDetails = mealsDetail

                            youtubePlayer(mealsDetail.strYoutube)
                            updateFav(mealsDetail)
                        }
                    }
                    hideLoading()
                }

                Status.ERROR-> {
                    noConnection( response.message == Constants.NO_NETWORK_CONNECTION)
                  //  networkError( response.message == Constants.OTHER_ERROR)
                }

                Status.LOADING -> {
                    showLoading()
                }
            }
        })
    }

    private fun takeMealsDetailFromDb() {
        viewModel.getMealDetailFromDb(mealID.toInt())
        viewModel.mealDetailDb.observe(viewLifecycleOwner, Observer {mealsDetailDb->
            when(mealsDetailDb.status) {
                Status.SUCCESS-> {
                    mealsDetailDb.data?.let {
                        binding.mealDetails = it
                        val ingredientAndMeasures = viewModel.makeIngAndMeaList(it)
                        adapter.ingredientAndMeasures = ingredientAndMeasures

                        youtubePlayer(it.strYoutube)
                        updateFav(it)
                    }
                    hideLoading()
                }

                Status.ERROR-> {

                }

                Status.LOADING -> {
                    showLoading()
                }
            }
        })
    }

    private fun youtubePlayer(url : String?) {
        binding.apply {
            if(url != null) {
                mealDetailPlayVideo.visibility = View.VISIBLE
                mealDetailPlayVideo.setOnClickListener {
                    if (networkHelper.isNetworkConnected()) {
                        val videoId = url.split("=")[1]
                        findNavController().navigate(
                            MealDetailFragmentDirections.
                            actionMealDetailFragmentToVideoFragment(videoId)
                        )

                    } else {
                        Toast.makeText(requireContext(),
                            getString(R.string.youtube_disconnected_msg), Toast.LENGTH_LONG).show()
                    }
                }

            } else {
                mealDetailPlayVideo.visibility = View.GONE
            }
        }
    }

    private fun updateFav(mealsDetail: MealsDetail) {
        viewModel.checkFav(mealID.toInt()).observe(viewLifecycleOwner, Observer {favStatus->
            binding.apply {
                detailFav.setOnClickListener {
                    if (favStatus) {
                        if(dbFlag) {
                            findNavController().popBackStack()
                        }
                        viewModel.deleteMealsDetail(mealID.toInt())
                        viewModel.deleteMeals(mealID.toInt())

                    } else {
                        viewModel.insertMeals(mealsDetail)
                        viewModel.insertMealsDetail(mealsDetail)
                    }
                }
                if (favStatus) {
                    detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.tartOrange))

                } else {
                    detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        })
    }

    private fun showLoading() {
        binding.mealDetailProgressBar.visibility = View.VISIBLE
        binding.detailContentLay.visibility = View.GONE
    }


    private fun hideLoading() {
        binding.mealDetailProgressBar.visibility = View.GONE
        binding.detailContentLay.visibility = View.VISIBLE
    }


    private fun noConnection(errorFlag: Boolean) {
        binding.apply {
            if (errorFlag) {
                detailContentLay.visibility = View.GONE
                mealDetailErrorDisplay.visibility = View.VISIBLE
                mealDetailProgressBar.visibility = View.GONE
                disconnectLay.imgDisconnect.setAnimation(R.raw.nointernet)
                disconnectLay.imgDisconnect.playAnimation()

            } else {
                detailContentLay.visibility = View.VISIBLE
                mealDetailErrorDisplay.visibility = View.GONE
            }
        }
    }

    private fun networkError(errorFlag: Boolean) {
        binding.apply {
            if (errorFlag) {
                detailContentLay.visibility = View.GONE
                mealDetailErrorDisplay.visibility = View.VISIBLE
                mealDetailProgressBar.visibility = View.GONE
                disconnectLay.imgDisconnect.setAnimation(R.raw.nointernet)
                disconnectLay.imgDisconnect.playAnimation()

            } else {
                detailContentLay.visibility = View.VISIBLE
                mealDetailErrorDisplay.visibility = View.GONE
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}