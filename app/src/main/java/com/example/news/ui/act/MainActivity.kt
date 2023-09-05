package com.example.news.ui.act

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import com.example.news.service.MyService
import com.example.news.utils.Constants.BROADCAST_STRING_FOR_ACTION
import com.example.news.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var mIntentFilter: IntentFilter

    private val mainViewModel: MainViewModel by viewModels()
    private var checkInternet = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //service
        mIntentFilter = IntentFilter()
        mIntentFilter.addAction(BROADCAST_STRING_FOR_ACTION)
        val intent = Intent(this, MyService::class.java)
        startService(intent)

        obServerLiveDataActivity()

        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHost.navController

        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.videoFragment,
                R.id.covidFragment,
                R.id.settingFragment
            )
        )
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.favoriteFragment || destination.id == R.id.findFragment || destination.id == R.id.playVideoFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

    }

    private fun obServerLiveDataActivity() {
        mainViewModel.checkInternet.observe(this){
            if(it != checkInternet){
                checkInternet = if(it) {
                    showSnackBarMess("We're back online!")
                    it
                }else{
                    showSnackBarMess("No internet connection..")
                    it
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private fun MyReceiver(): BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p1!!.action!! == BROADCAST_STRING_FOR_ACTION) {
                if (p1.getStringExtra("online_status").equals("true")) {
                    if (mainViewModel.checkInternet.value == false) {
                        mainViewModel.checkInternet.value = true
                    }

                } else {
                    if (mainViewModel.checkInternet.value == true) {
                        mainViewModel.checkInternet.value = false
                    }
                }
            }
        }
    }

    private fun showSnackBarMess(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    override fun onRestart() {
        super.onRestart()
        registerReceiver(MyReceiver(), mIntentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(MyReceiver())
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(MyReceiver(), mIntentFilter)
    }
}