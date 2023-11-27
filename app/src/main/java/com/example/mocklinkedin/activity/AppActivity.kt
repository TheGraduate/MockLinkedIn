package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.mocklinkedin.R

class AppActivity : AppCompatActivity(R.layout.activity_app){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController(R.id.nav_host_fragment).navigateUp()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                findViewById<ImageView>(R.id.profile).visibility = View.VISIBLE
                findViewById<ImageView>(R.id.enterExit).visibility = View.VISIBLE
                //findViewById<TabLayout>(R.id.tabLayout).visibility = TabLayout.VISIBLE
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}