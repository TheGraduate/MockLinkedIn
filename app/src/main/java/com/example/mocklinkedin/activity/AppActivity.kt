package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.auth.AppAuth
import com.example.mocklinkedin.viewmodel.AuthViewModel

class AppActivity : AppCompatActivity(R.layout.activity_app){
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.enter_exit_menu, menu)

        menu.let {
            it.setGroupVisible(R.id.unauthenticated, !viewModel.authenticated)
            it.setGroupVisible(R.id.authenticated, viewModel.authenticated)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragmentManager = supportFragmentManager

        return when (item.itemId) {
            android.R.id.home -> {
                findNavController(R.id.nav_host_fragment).navigateUp()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                findViewById<ImageView>(R.id.profile).visibility = View.VISIBLE
                //findViewById<ImageView>(R.id.log_in).visibility = View.VISIBLE
                //findViewById<TabLayout>(R.id.tabLayout).visibility = TabLayout.VISIBLE
                return true
            }
            R.id.signin -> {
                findViewById<ImageView>(R.id.profile).visibility = View.GONE
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_loginFragment)
                AppAuth.getInstance().setAuth(5, "x-token")
                //item.isVisible = false
                invalidateOptionsMenu()
                true
            }
            R.id.signup -> {

                findViewById<ImageView>(R.id.profile).visibility = View.GONE
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_registrationFragment)
                AppAuth.getInstance().setAuth(5, "x-token")
                invalidateOptionsMenu()
                true
            }
            R.id.signout -> {
                val dialogFragment = AuthAskFragment()
                dialogFragment.show(fragmentManager, "my_ask_fragment_tag")
                invalidateOptionsMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}