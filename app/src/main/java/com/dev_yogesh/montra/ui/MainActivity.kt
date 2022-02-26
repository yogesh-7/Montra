package com.dev_yogesh.montra.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.ActivityMainBinding
import com.dev_yogesh.montra.ui.fragment.onBoardingFragment.OnBoardingFragment
import com.dev_yogesh.montra.utils.changeStatusBarColor
import com.dev_yogesh.montra.utils.changeStatusBarTextColor


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() // hide the title bar
        /* this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN) //enable full screen
 */
        changeStatusBarColor(R.color.light_100, this)
        changeStatusBarTextColor(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)


        //makeStatusBarTransparent()
        this
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container_on_boarding, OnBoardingFragment.getInstance())
            .commit()


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.onBoardingFragment,
                -> {
                    binding.bottomNavigationView.isVisible = false
                    hideBottomNav(false)
                }
                R.id.homeFragment,
                -> {
                    binding.bottomNavigationView.isVisible = true
                    hideBottomNav(true)
                }
                else -> {
                    hideBottomNav(false)
                }
            }
        }

        binding.bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)


    }

    private fun hideBottomNav(visibility: Boolean) {
        binding.apply {
            bottomAppBar.isVisible = visibility
            fab.isVisible = visibility
        }
    }



}