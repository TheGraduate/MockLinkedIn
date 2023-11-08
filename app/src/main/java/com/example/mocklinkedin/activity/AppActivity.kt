package com.example.mocklinkedin.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import com.example.mocklinkedin.R
import com.example.mocklinkedin.activity.NewPostFragment.Companion.textArg

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//import com.example.mocklinkedin.adapter.DemoCollectionAdapter

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        navController.graph = navGraph

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.feedFragment , R.id.eventsFragment, R.id.usersListFragment -> {
                    showBottomNavigationBar()
                }
                else -> {
                    hideBottomNavigationBar()
                }
            }
        }

        setSupportActionBar(/*customToolbar*/findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val profileButton = findViewById<ImageView>(R.id.profile)
        val newPostButton = findViewById<ImageView>(R.id.newpost)

       /* val viewPager: ViewPager2 = findViewById(R.id.pager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Tab 1"
                1 -> tab.text = "Tab 2"
                2 -> tab.text = "Tab 3"
            }
        }.attach()*/


       fun hideButtons() {

            profileButton.visibility = View.GONE
            newPostButton.visibility = View.GONE
        }
        fun showButtons() {
            profileButton.visibility = View.VISIBLE
            newPostButton.visibility = View.VISIBLE
        }

        /*showButtons()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        invalidateOptionsMenu()*/

        profileButton.setOnClickListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_feedFragment_to_profileFragment)
            //tabLayout.visibility = TabLayout.GONE
            hideButtons()
        }
        newPostButton.setOnClickListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_feedFragment_to_newPostFragment)
            hideButtons()
            //tabLayout.removeTab()
            //tabLayout.visibility = TabLayout.GONE
        }




        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() != true) {
                return@let
            }

           intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    textArg = text
                }
            )
        }

            // todo при нажатии на кнопку FeedFragment в bottomNavigationView приложение крашится, также
            //  естественно крашится при нажатии на верхние кнопки, если фрагмент на экране(и в bottomNavigationView) не FeedFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.include_bottom_navigation_menu)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_feed -> {
                    val fragment = FeedFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_item_events -> {
                    val fragment = EventsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_item_users -> {
                    val fragment = UsersListFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun showBottomNavigationBar() {
        // Отобразить BottomNavigationBar, например, установить видимость на VISIBLE
        findViewById<BottomNavigationView>(R.id.include_bottom_navigation_menu).visibility = View.VISIBLE
        //include_bottom_navigation_menu.visibility = View.VISIBLE
    }

    private fun hideBottomNavigationBar() {
        findViewById<BottomNavigationView>(R.id.include_bottom_navigation_menu).visibility = View.GONE
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.post_list_menu, menu)
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController(R.id.nav_host_fragment).navigateUp()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                findViewById<ImageView>(R.id.profile).visibility = View.VISIBLE
                findViewById<ImageView>(R.id.newpost).visibility = View.VISIBLE
                //findViewById<TabLayout>(R.id.tabLayout).visibility = TabLayout.VISIBLE
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

   /* override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //findViewById<ImageView>(R.id.profile).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.newpost).visibility = View.VISIBLE
        // Здесь вы можете обновить кнопки на панели инструментов
        findViewById<ImageView>(R.id.profile).isVisible = true
    }*/
}