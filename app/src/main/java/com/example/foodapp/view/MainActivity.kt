package com.example.foodapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.util.base.BaseFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {

    @Inject lateinit var fragmentFactory : BaseFragmentFactory
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)

        supportFragmentManager.fragmentFactory = fragmentFactory

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}