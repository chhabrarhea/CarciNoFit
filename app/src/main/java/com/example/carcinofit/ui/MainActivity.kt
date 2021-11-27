package com.example.carcinofit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carcinofit.R
import com.example.carcinofit.api.ApiServiceInterface

import com.example.carcinofit.api.repository.ApiRepository
import com.example.carcinofit.api.viewmodel.ApiViewModel
import com.example.carcinofit.databinding.ActivityMainBinding
import com.example.carcinofit.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.carcinofit.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
    }

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.NavHost) as NavHostFragment
    }

    private val apiViewModel: ApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        apiViewModel.post.observe(this, {// simply observing the live data so whenever it gets notified of new data it passes it to the curly bracket '{}' where we can use it
            Log.d("Response from api", it.toString())
        })


        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        navController
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.profileFragment, R.id.homeFragment, R.id.statisticsFragment, R.id.historyFragment ->
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    else -> binding.bottomNavigationView.visibility = View.GONE
                }
            }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}