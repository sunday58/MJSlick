package com.mjslick

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    //declarations
    private lateinit var navController: NavController
    private lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolBar)

        //views
        val navView: BottomNavigationView = findViewById(R.id.customBottomBar)
        navView.inflateMenu(R.menu.menu)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_male, R.id.navigation_female, R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setDestinationListener()
        floatingButton()

    }

    private fun floatingButton(){
        female_cloths.setOnClickListener {
            Navigation.findNavController(customBottomBar).navigate(R.id.navigation_female)
        }
    }

    private fun setDestinationListener() {
        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest = resources.getResourceName(destination.id)
            Log.d("Item destination", dest)

            when (destination.id) {
                R.id.navigation_onBoarding,
                R.id.navigation_logIn,
                R.id.navigation_register -> {
                    hideCustomToolBar()
                    hideBottomNav()
                    hideFloatingButton()
                }
                R.id.navigation_male,
                R.id.navigation_female,
                R.id.navigation_profile -> run {
                    hideCustomToolBar()
                    showBottomNav()
                    showFloatingButton()
                    return@run
                }
                else -> {
                    showCustomToolBar()
                    showBottomNav()
                    showFloatingButton()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination!!.id == R.id.navigation_female ||
            navController.currentDestination!!.id == R.id.navigation_logIn)
            showDialog()
        else if (navController.currentDestination!!.id == R.id.navigation_profile ||
            navController.currentDestination!!.id == R.id.navigation_male)
            navController.navigate(R.id.navigation_female)
        else
            super.onBackPressed()
    }

    //handling leaving the app
    private fun showDialog() {
        val dialog = MaterialAlertDialogBuilder(this@MainActivity)
        dialog.setTitle("Exiting?")
        dialog.setIcon(R.drawable.ic_close)
        dialog.setMessage("Are you sure you want to exit?")
            .setPositiveButton(
                "YES"
            ) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                exitProcess(0)
            }
            .setNegativeButton(
                "NO"
            ) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
        dialog.create().show()
    }

    private fun hideCustomToolBar() {
       toolBar.isVisible = false
    }

    private fun showCustomToolBar() {
        toolBar.isVisible = true
    }

    private fun hideBottomNav(){
        customBottomBar.isVisible = false
    }

    private fun showBottomNav(){
        customBottomBar.isVisible = true
    }

    private fun hideFloatingButton(){
        female_cloths.isVisible = false
    }

    private fun showFloatingButton(){
        female_cloths.isVisible = true
    }

}