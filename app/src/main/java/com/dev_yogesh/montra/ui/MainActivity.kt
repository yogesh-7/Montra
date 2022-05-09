package com.dev_yogesh.montra.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.ActivityMainBinding
import com.dev_yogesh.montra.local.datastore.UIModeImpl
import com.dev_yogesh.montra.repo.TransactionRepo
import com.dev_yogesh.montra.services.exportcsv.ExportCsvService
import com.dev_yogesh.montra.ui.fragment.onBoardingFragment.OnBoardingFragment
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel
import com.dev_yogesh.montra.utils.changeStatusBarColor
import com.dev_yogesh.montra.utils.changeStatusBarTextColor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var repo: TransactionRepo
    @Inject
    lateinit var exportCsvService: ExportCsvService
    @Inject
    lateinit var themeManager: UIModeImpl
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() // hide the title bar
        changeStatusBarColor(R.color.light_100, this)
        changeStatusBarTextColor(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /**
         * Just so the viewModel doesn't get removed by the compiler, as it isn't used
         * anywhere here for now
         */
        viewModel

        navigationBottomBarSetup()
       // observeThemeMode()

    }

    private fun observeThemeMode() {
        lifecycleScope.launchWhenStarted {
            viewModel.getUIMode.collect {
                val mode = when (it) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }


    private fun navigationBottomBarSetup()= with(binding){
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.onBoardingFragment,
                -> {
                    bottomNavigationView.isVisible = false
                    hideBottomNav(false)
                }
                R.id.homeFragment,
                -> {
                    bottomNavigationView.isVisible = true
                    hideBottomNav(true)
                }
                else -> {
                    hideBottomNav(false)
                }
            }
        }

        bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)



    }

    private fun hideBottomNav(visibility: Boolean) {
        binding.apply {
            bottomAppBar.isVisible = visibility
            fab.isVisible = visibility
        }
    }



}