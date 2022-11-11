package com.yo.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yo.newsapp.databinding.ActivityMainBinding
import com.yo.newsapp.repository.NewsRepository
import com.yo.newsapp.repository.db.ArticleDatabase
import com.yo.newsapp.viewModel.NewsViewModel
import com.yo.newsapp.viewModel.NewsViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvider)[NewsViewModel::class.java]

//        binding.bottomNavigationView.setupWithNavController(binding.newsFragment.findNavController())

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}