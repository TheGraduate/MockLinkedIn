package com.example.mocklinkedin.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.doOnDetach
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.example.mocklinkedin.R
import com.example.mocklinkedin.activity.NewPostFragment.Companion.textArg
import com.example.mocklinkedin.databinding.ActivityAppBinding
import com.example.mocklinkedin.databinding.CustomToolbarBinding
import com.example.mocklinkedin.databinding.FragmentFeedBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//import com.example.mocklinkedin.adapter.DemoCollectionAdapter

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    //private lateinit var customToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //invalidateOptionsMenu()// обновление меню
        //setContentView(R.layout.activity_app)
        //customToolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(/*customToolbar*/findViewById(R.id.toolbar))//устанавливает кастомный тулбар в качестве actionbar(без этого не будет работать встроенная кнопка назад)
        supportActionBar?.setDisplayShowTitleEnabled(false)//Выключение названия приложения в action bar
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)// Включение кнопки "назад"

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
        }.attach()*/ // todo view pager


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


        /*val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    // Замените текущий фрагмент на фрагмент, связанный с пунктом 1
                    val fragment = BlankFragment1()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_item2 -> {
                    // Замените текущий фрагмент на фрагмент, связанный с пунктом 2
                    val fragment = BlankFragment2()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                // Добавьте обработку других пунктов меню по мере необходимости
                else -> return@setOnItemSelectedListener false
            }
        }*/


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
           /* R.id.remove -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                findViewById<ImageView>(R.id.profile).visibility = View.VISIBLE
                findViewById<ImageView>(R.id.newpost).visibility = View.VISIBLE
                return true
            }*/
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